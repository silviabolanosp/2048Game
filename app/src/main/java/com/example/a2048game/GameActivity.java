package com.example.a2048game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.Chronometer;
import android.os.SystemClock;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity{

    private static GameActivity gameActivity = null;
    public static Save database = new Save();

    public static GameActivity getGameActivity() {
        return gameActivity;
    }

    Chronometer simpleChronometer;
    TextView timer;
    MyCount counter;
    long timeWhenStopped;
    MediaPlayer mediaPlayer;
    MediaPlayer swipeSound;
    Bundle userName;
    Bundle nameGame;
    private TextView user;
    private Switch musicSwitch;

    private int score = 0;
    private int minutes = 0;
    private TextView scoreLabel= null;
    private TextView highestBlock;
    private GameView6x6 grid6x6;
    private GameView grid4x4;
    private GameViewBomb gridBomb;



    public GameActivity()
        {
            gameActivity = this;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        timer = (TextView) findViewById(R.id.timer);
        minutes = extras.getInt(MainActivity.EXTRA_MINUTES);
        scoreLabel= (TextView) findViewById(R.id.Score);
        highestBlock = (TextView) findViewById(R.id.highestBlock);
        Button btnCancel = findViewById(R.id.btnCancel);


        if (minutes == 0){
            timer.setVisibility(View.GONE);
            simpleChronometer.start();
        }else{
            simpleChronometer.setVisibility(View.GONE);
            int milliseconds = minutes * 60 * 1000;
            counter = new MyCount(milliseconds, 1000);
            counter.start();
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

                if (minutes == 0){
                    simpleChronometer.stop();
                    timeWhenStopped = 0;
                    timeWhenStopped = simpleChronometer.getBase() - SystemClock.elapsedRealtime();
                }else{
                    counter.pause();
                }

                //falta poner el counter en pausa
                builder.setTitle("¿Terminar partida?");
                builder.setMessage("¿Desea terminar partida?");

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                simpleChronometer.stop();
                                close();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
//                                if (minutes == 0){
//                                    simpleChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
//                                    simpleChronometer.start();
//                                }else{
//                                    counter.resumen();
//                                }
//                                break;

                            case DialogInterface.BUTTON_NEUTRAL:
                                if (minutes == 0){
                                    simpleChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                                    simpleChronometer.start();
                                }else{
                                    counter.resumen();
                                }

                                break;
                        }
                    }
                };

                builder.setPositiveButton("Si", dialogClickListener);

                builder.setNegativeButton("No",dialogClickListener);

                builder.setNeutralButton("Cancelar", dialogClickListener);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        grid4x4= (GameView) findViewById(R.id.gameView4x4);
        grid6x6= (GameView6x6) findViewById(R.id.gameView6x6);
        gridBomb= (GameViewBomb) findViewById(R.id.gameViewBomb);

        int gridSize = extras.getInt(MainActivity.EXTRA_GRID_SIZE);

        switch (gridSize){
            case 2:
                grid6x6.setVisibility(View.GONE);
                grid4x4.setVisibility(View.GONE);
                break;
            case 4:
                grid6x6.setVisibility(View.GONE);
                gridBomb.setVisibility(View.GONE);
                break;
            case 6:
                grid4x4.setVisibility(View.GONE);
                gridBomb.setVisibility(View.GONE);
                break;
        }


        musicSwitch = (Switch) findViewById(R.id.music);

        boolean musicBoolean = extras.getBoolean(MainActivity.EXTRA_MUSIC);
        musicSwitch.setChecked(musicBoolean);

        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicSwitch.isChecked()) {
                    mediaPlayer.start();
                }else{
                    mediaPlayer.pause();
                }

            }
        });



    }

    public void clearScore(){
        score = 0;
        showScore();
    }

    public void showScore(){

        scoreLabel.setText("" + new DecimalFormat("#").format(score));
    }

    public void addScore(double s){
        score += s;
        showScore();
    }


    public void showHighestBlock(double score, int color){
        highestBlock.setBackgroundColor(color);
        highestBlock.setText(""+ new DecimalFormat("#").format(score));
    }


    //countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer{
        TextView timer = (TextView) findViewById(R.id.timer);
        private boolean isPaused = false;
        private long secLeft;

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            secLeft = millisInFuture;
        }

        @Override
        public void onFinish() {
            timer.setText("0!");
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

            builder.setTitle("Se agotó el tiempo");

            builder.setMessage("¿Desea ir al menú?");

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which){
                        case DialogInterface.BUTTON_POSITIVE:
                            close();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            close();
                            break;

                        case DialogInterface.BUTTON_NEUTRAL:
                            close();
                            break;
                    }
                }
            };

            builder.setPositiveButton("Si", dialogClickListener);

            builder.setNegativeButton("No",dialogClickListener);

            builder.setNeutralButton("Cancelar", dialogClickListener);

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(!isPaused){
                timer.setText("" + secLeft/1000);
                secLeft = secLeft - 1000;
            }


        }

        public void pause() {
            isPaused = true;
            timer.setText("" + secLeft/1000);

        }

        public void resumen(){
            isPaused = false;
        }

    }


    public void save(){
        Bundle nameGame = getIntent().getExtras();
        Bundle userName = getIntent().getExtras();
        String name = nameGame.getString("nameGame");
        String user = userName.getString("user");
        String s= scoreLabel.getText().toString();
        int score = Integer.parseInt(s);
        Game g = new Game();
        g.setName(name);
        g.setScore(score);
        g.setTime(234);
        g.setUser(user);
        g.setType("Normal");
        database.saveGame(g);
    }

    public void close()
    {
        //mediaPlayer.pause();
        clearScore();
        Bundle userName = getIntent().getExtras();
        String user = userName.getString("user");
        save();
        Intent i= new Intent(GameActivity.this,MainActivity.class);
        i.putExtra("userName",user);
        startActivity(i);

    }

    public void swipeNoise(){
        // SWIPE NOISE EFFECT
        swipeSound = MediaPlayer.create(getApplicationContext(), R.raw.swipe1);
        swipeSound.start();
    }

}
