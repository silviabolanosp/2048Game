package com.example.a2048game;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.media.MediaPlayer;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {
    private TextView nameGame;
    private TextView user;
    Button btnStart;
    Button btnScores;
    Button btnTutorial;
    MediaPlayer mediaPlayer;
    Switch timerSwitch;
    Switch colorSwtich;
    Switch musicSwitch;
    Switch gridSizeSwitch;
    ConstraintLayout layout;
    boolean darkMode = false;
    NumberPicker np;

    int minutes = 0;
    int gridSize = 0;
    public static final String EXTRA_MINUTES = "com.example.a2048game.MINUTES";
    public static final String EXTRA_GRID_SIZE = "com.example.a2048game.GRID_SIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main);
        Bundle userName = getIntent().getExtras();
        final String nameUser = userName.getString("userName");
        user = findViewById(R.id.txtUserName);
        user.setText(nameUser.toString());
        nameGame= (TextView) findViewById(R.id.user);
        timerSwitch = (Switch) findViewById(R.id.timer);
        gridSizeSwitch = (Switch) findViewById(R.id.gridSize);

        timerSwitch = findViewById(R.id.timer);

        colorSwtich = findViewById(R.id.colorSwitch);
        colorSwtich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
            }
        });
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameGame.getText().toString().equals("")) {
                    nameGame.setText(user.getText().toString());
                }

                if (timerSwitch.isChecked()) {
                    minutes = np.getValue();
                }else {
                    minutes = 0;
                }


                if (gridSizeSwitch.isChecked() == true){ //4x4
                    gridSize = 4;
                }
                else{ // 6x6
                    gridSize = 6;
                }

                Intent nextView = new Intent(MainActivity.this,GameActivity.class);
                nextView.putExtra("nameGame",nameGame.getText().toString());
                nextView.putExtra("user", user.getText().toString());
                nextView.putExtra("mode", darkMode);
                nextView.putExtra(EXTRA_MINUTES, minutes);
                nextView.putExtra(EXTRA_GRID_SIZE, gridSize);
                startActivity(nextView);

            }
        });

        btnScores = findViewById(R.id.btnScores);
        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoresView = new Intent(MainActivity.this,ResultPage.class);
                scoresView.putExtra("user", user.getText().toString());
                startActivity(scoresView);
            }
        });

        btnTutorial = findViewById(R.id.btnTutorial);
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorialView = new Intent(MainActivity.this,Tutorial.class);
                tutorialView.putExtra("user", user.getText().toString());
                startActivity(tutorialView);
            }
        });

        // NUMBER PICKER
        np = (NumberPicker) findViewById(R.id.np);
        np.setMinValue(1);
        np.setMaxValue(30);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){

            }
        });

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mediaPlayer.start();

        musicSwitch = (Switch) findViewById(R.id.music);

        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (musicSwitch.isChecked()){
                    mediaPlayer.start();
                }
                else{
                    mediaPlayer.pause();
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    protected void changeColor(){
        layout = findViewById(R.id.main);
        if (colorSwtich.isChecked()) {
            layout.setBackgroundColor(Color.parseColor("#424242"));
            nameGame.setTextColor(Color.parseColor("#FBFEF9"));
            darkMode = true;
        }else {
            layout.setBackgroundColor(Color.parseColor("#F7F7EA"));
            nameGame.setTextColor(Color.parseColor("#5603AD"));
            darkMode = false;
        }


    }

}