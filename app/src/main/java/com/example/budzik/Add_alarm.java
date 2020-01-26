package com.example.budzik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_alarm extends AppCompatActivity {

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);

        timePicker = findViewById(R.id.clock);

       if(MainActivity.existing.equals("yes")){
           Intent i=getIntent();
           timePicker.setCurrentHour(i.getIntExtra("hour",0));
           timePicker.setCurrentMinute(i.getIntExtra("minute",0));
       }

        timePicker.setIs24HourView(true);
        Button apply = findViewById(R.id.button_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time;
                time=Alarm();
                if(MainActivity.existing.equals("yes")){
                    Intent i=getIntent();
                    TaskListContent.removeItem(i.getIntExtra("position",1));
                }

                TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1,time));
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                Toast.makeText(getApplicationContext(),"Alarm added!",Toast.LENGTH_SHORT).show();
                Intent alarm=new Intent(Add_alarm.this,MainActivity.class);
                setResult(RESULT_OK,alarm);
                finish();
            }
        });

    }


    public String Alarm()
    {
        Integer currentHour = timePicker.getCurrentHour();
        Integer currentMinute = timePicker.getCurrentMinute();

        String text;

        if(currentMinute<10 && currentHour<10){
            text="0".concat(currentHour.toString()).concat(":0").concat(currentMinute.toString());
        }
        else if(currentMinute<10){
            text= currentHour.toString().concat(":0").concat(currentMinute.toString());
        }
        else if(currentHour<10){
            text="0".concat(currentHour.toString()).concat(":").concat(currentMinute.toString());
        }
        else
        {
            text= currentHour.toString().concat(":").concat(currentMinute.toString());
        }
        return text;
    }
}

