package com.example.a2048game;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;


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
    MediaPlayer swipeSound;
    Bundle userName;
    Bundle nameGame;
    private TextView user;
    int score = 0;
    private int minutes = 0;
    TextView scoreLabel= null;
    TextView highestBlock;
    GameView6x6 grid6x6;
    GameView grid4x4;
    ConstraintLayout layout;
    GameViewBomb gridBomb;
    String modeGame;
    Bundle extras;
    Bundle mode;
    boolean darkMode;



    public GameActivity()
        {
            gameActivity = this;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        nameGame = getIntent().getExtras();
        userName = getIntent().getExtras();
        mode = getIntent().getExtras();
        darkMode = mode.getBoolean("mode");
        changeMode();
        final String nameUser = userName.getString("user");
        user = findViewById(R.id.txtUserName);
        user.setText(nameUser.toString());
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
                modeGame = "Bomba";
                break;
            case 4:
                grid6x6.setVisibility(View.GONE);
                gridBomb.setVisibility(View.GONE);
                modeGame = "Normal";
                break;
            case 6:
                grid4x4.setVisibility(View.GONE);
                gridBomb.setVisibility(View.GONE);
                modeGame = "6x6";
                break;
        }

    }

    private void changeMode() {
        layout = findViewById(R.id.wholelayout);
        if(darkMode == true){
            layout.setBackgroundColor(Color.parseColor("#424242"));
        }else{
            layout.setBackgroundColor(Color.parseColor("#F7F7EA"));
        }
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
        if(minutes == 0){
            g.setTime(simpleChronometer.getText().toString());
        }else{
            g.setTime(Integer.toString(minutes) + ":00");
        }

        g.setScore(score);
        g.setUser(user);
        g.setType(modeGame);
        database.saveGame(g);
    }

    public void close()
    {
        Bundle userName = getIntent().getExtras();
        String user = userName.getString("user");
        save();
        clearScore();
        Intent i= new Intent(GameActivity.this,MainActivity.class);
        i.putExtra("userName",user);
        i.putExtra("mode",darkMode);
        startActivity(i);

    }

    public void swipeNoise(){
        // SWIPE NOISE EFFECT
        swipeSound = MediaPlayer.create(getApplicationContext(), R.raw.swipe1);
        swipeSound.start();
    }

}
