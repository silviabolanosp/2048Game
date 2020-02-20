package com.example.a2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.NumberPicker;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private TextView username;

    //Referencia a la base de datos
    DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    DatabaseReference rootChild = myDatabase.child("users");

    MediaPlayer mediaPlayer;
    Switch timerSwitch;
    NumberPicker np;
    int minutes = 0;

    public static final String EXTRA_MINUTES = "com.example.a2048game.MINUTES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username= (TextView) findViewById(R.id.user);
        timerSwitch = (Switch) findViewById(R.id.timer);

        final Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (timerSwitch.isChecked())
                    minutes = np.getValue();
                else
                    minutes = 0;



                Intent nextView = new Intent(MainActivity.this,GameActivity.class);
                nextView.putExtra(EXTRA_MINUTES, minutes);
                startActivity(nextView);


            }
        });


        //Get the widgets reference from XML layout
        final TextView tv = (TextView) findViewById(R.id.tv);
        np = (NumberPicker) findViewById(R.id.np);

        //Set TextView text color
        tv.setTextColor(Color.parseColor("#ffd32b3b"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(30);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                tv.setText("Minutos : " + newVal);
            }
        });

        // MUSIC
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mediaPlayer.start();
    }

    @Override
    protected void onStart(){
        super.onStart();
        rootChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue().toString();
                username.setText(user);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
}
