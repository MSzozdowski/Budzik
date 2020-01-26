package com.example.budzik;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Question extends AppCompatActivity {

    int number;
    String correct_answer;
    int to_play=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogg);
        final Ringtone ring;
        ring= RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(1));
        Random rand = new Random();
        number = rand.nextInt(4);
        TextView choosen=findViewById(R.id.textView2);
        choosen.setText(Integer.toString(number+1));

        switch(number){
            case 0:
                correct_answer="4";
                break;
            case 1:
                correct_answer="6";
                break;
            case 2:
                correct_answer="8";
                break;
            case 3:
                correct_answer="Rome";
                break;
        }


        if(!ring.isPlaying())
        {
            ring.play();
        }


        Button confirm=findViewById(R.id.button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=findViewById(R.id.editText);
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                if(editText.getText().toString().equals(correct_answer)){
                    to_play=0;
                    if(to_play==0){
                        if(ring.isPlaying()){
                            ring.stop();
                            Toast.makeText(getApplicationContext(),"Alarm deleted!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    //startActivity(intent);
                    Intent alarm=new Intent(Question.this,MainActivity.class);
                    setResult(RESULT_OK,alarm);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Try again!",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}