package com.example.budzik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.TimePickerDialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Add_alarm extends AppCompatActivity {

    TextClock actualTime;
    TimePicker timePicker;
    RadioGroup sounds;
    RadioButton sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);

        Intent intent = getIntent();

        actualTime = findViewById(R.id.time);
        timePicker = findViewById(R.id.clock);

        sounds = findViewById(R.id.sounds);
        Button apply = findViewById(R.id.button_sound);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioID = sounds.getCheckedRadioButtonId();

                sound = findViewById(radioID);
            }
        });


        timePicker.setIs24HourView(true);

        final Ringtone ring = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        //#TODO DODAC SOWJE DZIEWKI I SPINNERA DO DZWIEKÓW

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (actualTime.getText().toString().equals(Alarm())) {
                    ring.play();
                } else {
                    ring.stop();
                }
            }
        }, 0, 1000);

    }

    public String Alarm()
    {
        Integer currentHour = timePicker.getCurrentHour();
        Integer currentMinute = timePicker.getCurrentMinute();

        String text;
        text= currentHour.toString().concat(":").concat(currentMinute.toString());

        return text;
    }

    public void checkButton(View v){
        int radioID=sounds.getCheckedRadioButtonId();

        sound=findViewById(radioID);
    }
}

