package com.example.a2048game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.Chronometer;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;
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
    Bundle userName;
    Bundle nameGame;
    private TextView user;
    TextView t = null;
    TextView highestBlock;
    int score=0;


    public GameActivity() {
        gameActivity = this;
    }

/*        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.activity_game);
            super.onCreate(savedInstanceState);
            nameGame = getIntent().getExtras();
            userName = getIntent().getExtras();
            final String nameUser = userName.getString("user");
            user = findViewById(R.id.txtUserName);
            user.setText(nameUser.toString());
            t=(TextView) findViewById(R.id.Score);
            GridView grid=(GridView)findViewById(R.id.gamebackground);
            Random random1=new Random();
            int r1 = random1.nextInt(16-1+1)+1;
            Random random2=new Random();
            int r2 = random2.nextInt(16-1+1)+1;
            if(r1==r2 && r2!=16)
            {
                r2++;
            }
            else
            {
                if(r1==r2)
                {
                    r2--;
                }
            }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        nameGame = getIntent().getExtras();
        userName = getIntent().getExtras();
        final String nameUser = userName.getString("user");
        user = findViewById(R.id.txtUserName);
        user.setText(nameUser.toString());
        t = (TextView) findViewById(R.id.Score);

        highestBlock = (TextView) findViewById(R.id.highestBlock);

        Button btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                timeWhenStopped = 0;

                timeWhenStopped = simpleChronometer.getBase() - SystemClock.elapsedRealtime();
                simpleChronometer.stop();

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
                                simpleChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                                simpleChronometer.start();
                                break;

                            case DialogInterface.BUTTON_NEUTRAL:
                                simpleChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                                simpleChronometer.start();

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

        // 5000 is the starting number (in milliseconds)
        // 1000 is the number to count down each time (in milliseconds)

        Bundle extras = getIntent().getExtras();
        int minutes = extras.getInt(MainActivity.EXTRA_MINUTES);
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        timer = (TextView) findViewById(R.id.timer);

        if (minutes == 0){
            timer.setVisibility(View.GONE);
            //simpleChronometer.setFormat("Time (%s)");
            simpleChronometer.start();
            // simpleChronometer.stop();
        }else{
            simpleChronometer.setVisibility(View.GONE);
            int milliseconds = minutes * 60 * 1000;
            counter = new MyCount(milliseconds, 1000);
            counter.start();
        }


    }

    public void clearScore(){
        score = 0;
        showScore();
    }

    public void showScore(){

        t.setText(score + "");
    }

    public void addScore(int s){
        score += s;
        showScore();
    }


    public void showHighestBlock(int score, int color){
        highestBlock.setBackgroundColor(color);
        highestBlock.setText(""+ score);
    }


    //countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer{
        TextView timer = (TextView) findViewById(R.id.timer);

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
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
            timer.setText("" + millisUntilFinished/1000);

        }

    }


    public void save(){
        Bundle nameGame = getIntent().getExtras();
        Bundle userName = getIntent().getExtras();
        String name = nameGame.getString("nameGame");
        String user = userName.getString("user");
        String s= t.getText().toString();
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
        clearScore();
        Bundle userName = getIntent().getExtras();
        String user = userName.getString("user");
        save();
        Intent i= new Intent(GameActivity.this,MainActivity.class);
        i.putExtra("userName",user);
        startActivity(i);

    }

}
