package com.example.a2048game;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.media.MediaPlayer;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    public MediaPlayer mediaPlayer;

    private TextView nameGame;
    private TextView user;
    private TextView menu;
    private Button btnStart;
    private Button btnScores;
    private Button btnTutorial;
    private Switch timerSwitch;
    //private Switch musicSwitch;
    //private Switch gridSizeSwitch;
    //private Switch bombSwitch;
    //Switch colorSwtich;
    ConstraintLayout layout;
    boolean darkMode = false;
    Bundle mode;

    private NumberPicker np;
    private int minutes = 0;
    private int gridSize = 0;
    //private boolean musicBoolean = true;
    private boolean isNightMode = false;

    public static final String EXTRA_MINUTES = "com.example.a2048game.MINUTES";
    public static final String EXTRA_GRID_SIZE = "com.example.a2048game.GRID_SIZE";
    //public static final String EXTRA_MUSIC = "com.example.a2048game.MUSIC";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main);
        Bundle userName = getIntent().getExtras();
        mode = getIntent().getExtras();
        darkMode = mode.getBoolean("mode");
        final String nameUser = userName.getString("userName");
        user = findViewById(R.id.txtUserName);
        menu = findViewById(R.id.textView);
        user.setText(nameUser.toString());
        nameGame = (TextView) findViewById(R.id.user);
        timerSwitch = (Switch) findViewById(R.id.timer);
        //gridSizeSwitch = (Switch) findViewById(R.id.gridSize);
        // bombSwitch = (Switch) findViewById(R.id.bombs);
        timerSwitch = findViewById(R.id.timer);
        //colorSwtich = findViewById(R.id.colorSwitch);


//        colorSwtich.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeColor();
//            }
//        });

        btnStart = findViewById(R.id.btnStart);
        layout = findViewById(R.id.main);
        //changeColorSecond();
        changeColor2();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameGame.getText().toString().equals("")) {
                    nameGame.setText(user.getText().toString());
                }

                if (timerSwitch.isChecked()) {
                    minutes = np.getValue();
                } else {
                    minutes = 0;
                }

                /*
                if (bombSwitch.isChecked() == true){
                    gridSize = 2;
                }else{
                    if (gridSizeSwitch.isChecked() == true){ //4x4
                        gridSize = 4;
                    }
                    else{ // 6x6
                        gridSize = 6;
                    }
                }
                */


//                if (musicSwitch.isChecked()){
//                    musicBoolean = true;
//                }
//                else{
//                    musicBoolean = false;
//                }


                Intent nextView = new Intent(MainActivity.this,GameActivity.class);
                nextView.putExtra("nameGame",nameGame.getText().toString());
                nextView.putExtra("user", user.getText().toString());
                nextView.putExtra("mode", darkMode);
                nextView.putExtra(EXTRA_MINUTES, minutes);
                nextView.putExtra(EXTRA_GRID_SIZE, gridSize);
                //nextView.putExtra(EXTRA_MUSIC, musicBoolean);
                startActivity(nextView);

            }
        });

        btnScores = findViewById(R.id.btnScores);
        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoresView = new Intent(MainActivity.this, ResultPage.class);
                scoresView.putExtra("user", user.getText().toString());
                scoresView.putExtra("mode", darkMode);
                startActivity(scoresView);
            }
        });

        btnTutorial = findViewById(R.id.btnTutorial);
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorialView = new Intent(MainActivity.this, Tutorial.class);
                tutorialView.putExtra("user", user.getText().toString());
                tutorialView.putExtra("mode", darkMode);
                startActivity(tutorialView);
            }
        });

        // NUMBER PICKER
        np = (NumberPicker) findViewById(R.id.np);
        np.setMinValue(1);
        np.setMaxValue(30);
        np.setWrapSelectorWheel(true);

        // TIMER O COUNTDOWN
        timerSwitch.setText(timerSwitch.getTextOn());

        timerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timerSwitch.isChecked()) {
                    timerSwitch.setText(timerSwitch.getTextOn());
                    np.setVisibility(View.VISIBLE);
                } else {
                    timerSwitch.setText(timerSwitch.getTextOff());
                    np.setVisibility(View.INVISIBLE);
                }

            }
        });


        // MUSIC
//
//        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music2);
//        mediaPlayer.start();
//
//        musicSwitch = (Switch) findViewById(R.id.music);
//
//        musicSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (musicSwitch.isChecked()) {
//                    mediaPlayer.start();
//                } else {
//                    mediaPlayer.pause();
//                }
//
//            }
//        });

//        // TAMANO DEL GRID
//        gridSizeSwitch.setText(gridSizeSwitch.getTextOn());
//
//        gridSizeSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (gridSizeSwitch.isChecked()){
//                    gridSizeSwitch.setText(gridSizeSwitch.getTextOn());
//                }
//                else{
//                    gridSizeSwitch.setText(gridSizeSwitch.getTextOff());
//                }
//
//            }
//        });
//
//        gridSizeSwitch.setChecked(true);
//        gridSizeSwitch.setText(gridSizeSwitch.getTextOn());
//        //gridSizeSwitch.setClickable(false);
//        gridSizeSwitch.setEnabled(false);



        /*
        // Deshabilitar grid de 6x6 si escoge bombas
        bombSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bombSwitch.isChecked()){
                    gridSizeSwitch.setChecked(true);
                    gridSizeSwitch.setText(gridSizeSwitch.getTextOn());
                    //gridSizeSwitch.setClickable(false);
                    gridSizeSwitch.setEnabled(false);
                }
                else{
                    //gridSizeSwitch.setClickable(true);
                    gridSizeSwitch.setEnabled(true);
                }

            }
        });
         */


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.gameModeBombs:
                if (checked)
                    gridSize = 2;
                    break;
            case R.id.gameMode4x4:
                if (checked)
                    gridSize = 4;
                    break;
            case R.id.gameMode6x6:
                if (checked)
                    gridSize = 6;
                    break;

        }
    }

    public void clickNightMode(View v)
    {
        isNightMode = !isNightMode;
        changeColor2();
    }

    protected void changeColor2 () {
        if (isNightMode) {
            layout.setBackgroundColor(Color.parseColor("#424242"));
            nameGame.setTextColor(Color.parseColor("#FBFEF9"));
            menu.setTextColor(Color.parseColor("#FBFEF9"));
        } else {
            layout.setBackgroundColor(Color.parseColor("#F7F7EA"));
            nameGame.setTextColor(Color.parseColor("#5603AD"));
            menu.setTextColor(Color.parseColor("#000000"));
        }
    }

//    protected void changeColor () {
//       if (colorSwtich.isChecked()) {
//           layout.setBackgroundColor(Color.parseColor("#424242"));
//           nameGame.setTextColor(Color.parseColor("#FBFEF9"));
//           menu.setTextColor(Color.parseColor("#FBFEF9"));
//           darkMode = true;
//        } else {
//           layout.setBackgroundColor(Color.parseColor("#F7F7EA"));
//           nameGame.setTextColor(Color.parseColor("#5603AD"));
//           menu.setTextColor(Color.parseColor("#000000"));
//           darkMode = false;
//       }
//    }
//
//    protected void changeColorSecond () {
//
//        if (darkMode == true){
//            layout.setBackgroundColor(Color.parseColor("#424242")); // dark color
//            nameGame.setTextColor(Color.parseColor("#FBFEF9")); //green but veeeeery white
//            menu.setTextColor(Color.parseColor("#FBFEF9")); //green but veeeeery white
//            darkMode = true;
//            colorSwtich.setChecked(darkMode);
//        } else {
//            layout.setBackgroundColor(Color.parseColor("#F7F7EA")); // light color
//            nameGame.setTextColor(Color.parseColor("#4FDB6F")); // green
//            menu.setTextColor(Color.parseColor("#35B4F9")); // blue
//            darkMode = false;
//        }
//    }
}