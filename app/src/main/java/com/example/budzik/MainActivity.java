package com.example.budzik;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{

    private final String TASKS_SHARED_PREFS="TasksSharedPrefs";
    private TaskListContent.Task currentTask;
    private final String CURRENT_TASK_KEY="CurrentTask";
    private final String NUM_TASKS="NumOfTasks",TASK="task_",ID="id_";
    public static String existing;
    int hour;
    int minute;
    TextClock actualTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences tasks=getSharedPreferences(TASKS_SHARED_PREFS,MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null) {
            currentTask=savedInstanceState.getParcelable(CURRENT_TASK_KEY);
        }

        restoreTasksFromSharedPreferences();

        BroadcastReceiver myReceiver1 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                List<String> times=TaskListContent.makeTimeList();
                int which_time=timeOK(times);
                if(which_time!=1000) {
                    Intent question=new Intent(getApplicationContext(),Question.class);
                    startActivity(question);
                }
            }
        };
        registerReceiver(myReceiver1, new IntentFilter(Intent.ACTION_TIME_TICK));



    }

    @Override
    protected void onPause(){
        SharedPreferences tasks=getSharedPreferences(TASKS_SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=tasks.edit();
        editor.apply();
        saveTasksToSharedPreferences();
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        SharedPreferences tasks=getSharedPreferences(TASKS_SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=tasks.edit();
        editor.apply();
        saveTasksToSharedPreferences();
        super.onDestroy();
    }

    @Override
    protected  void onSaveInstanceState(Bundle outState){
        if(currentTask!=null)
            outState.putParcelable(CURRENT_TASK_KEY,currentTask);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume(){
        ((ItemFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2)).notifyDataChange();
        super.onResume();
    }

    private void startAddAlarmActivity(TaskListContent.Task task, int position){
        Intent intent = new Intent(MainActivity.this,Add_alarm.class);

        hour = Integer.valueOf((task.content).substring(0,2));
        minute = Integer.valueOf((task.content).substring(3,5));

        intent.putExtra("hour",hour);
        intent.putExtra("minute",minute);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    private void startAddAlarmActivity(){
        Intent intent = new Intent(getApplicationContext(),Add_alarm.class);
        existing="no";
        startActivity(intent);
    }
    public void addClick(View view){
        startAddAlarmActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==Add_alarm.RESULT_OK){
            Intent intent = new Intent(getApplicationContext(),Add_alarm.class);
            startActivity(intent);
        }
    }

    @Override
    public void onListFragmentInteractionListener(TaskListContent.Task task, int position) {
        currentTask=task;
        existing="yes";
        startAddAlarmActivity(task,position);
    }
    @Override
    public void onListFragmentDeleteInteractionListener(TaskListContent.Task task, int position){
        currentTask=task;
        if(position!=-1 && position<TaskListContent.ITEMS.size()){
            TaskListContent.removeItem(position);
            ((ItemFragment)getSupportFragmentManager().findFragmentById(R.id.fragment2)).notifyDataChange();
        }

    }

    public int timeOK(List<String> times){
        actualTime = findViewById(R.id.time);
        for(int i=0; i<times.size();i++){
            if(actualTime.getText().toString().equals(times.get(i))){
                return i;
            }
        }
        return 1000;
    }

    //--------------------------------------------------------------------------------------------------
    private void saveTasksToSharedPreferences(){
        SharedPreferences tasks=getSharedPreferences(TASKS_SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=tasks.edit();
        editor.clear();
        editor.putInt(NUM_TASKS,TaskListContent.ITEMS.size());

        for(int i=0;i<TaskListContent.ITEMS.size();i++)
        {
            TaskListContent.Task task=TaskListContent.ITEMS.get(i);
            editor.putString(TASK+i,task.content);
            editor.putString(ID+1,task.id);
        }
        editor.apply();
    }

    private void restoreTasksFromSharedPreferences(){
        SharedPreferences tasks=getSharedPreferences(TASKS_SHARED_PREFS,MODE_PRIVATE);
        int numOfTasks=tasks.getInt(NUM_TASKS,0);

        if(numOfTasks!=0){
            TaskListContent.clearList();
            for(int i=0;i<numOfTasks;i++){
                String content=tasks.getString(TASK+i,"0");
                String id=tasks.getString(ID+i,"0");
                TaskListContent.addItem(new TaskListContent.Task(id,content));
            }
        }
    }
}

