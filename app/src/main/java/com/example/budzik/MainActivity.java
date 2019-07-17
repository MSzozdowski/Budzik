package com.example.budzik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void addClick(View view){
        Intent intent = new Intent(MainActivity.this,Add_alarm.class);
        MainActivity.this.startActivity(intent);
    }

}

