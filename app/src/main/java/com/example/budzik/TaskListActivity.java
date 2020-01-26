package com.example.budzik;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TaskListActivity extends AppCompatActivity {
    public static String DATA_CHANGED_KEY="dataSetChanged";
    private boolean Changed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
    }

    @Override
    public void onBackPressed(){
        setResult(RESULT_OK,new Intent().putExtra(DATA_CHANGED_KEY,Changed));
        super.onBackPressed();
    }
}
