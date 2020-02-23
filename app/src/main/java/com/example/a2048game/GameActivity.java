package com.example.a2048game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.Chronometer;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    int score=0;
    TextView t = null;



    public GameActivity()
        {
            gameActivity = this;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        t =(TextView) findViewById(R.id.Score);



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
                MyCount counter = new MyCount(milliseconds, 1000);
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


    //countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer{
        TextView timer = (TextView) findViewById(R.id.timer);

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            timer.setText("done!");
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
        Bundle userName = getIntent().getExtras();
        String user = userName.getString("user");
        save();
        Intent i= new Intent(GameActivity.this,MainActivity.class);
        i.putExtra("user",user);
        startActivity(i);

    }
    public void endGame()
    {
        ConstraintLayout cl=(ConstraintLayout) findViewById(R.id.wholelayout);
        LinearLayout lll= new LinearLayout(this);
        lll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lll.setBackgroundColor(getResources().getColor(R.color.translucent));
        lll.setGravity(Gravity.CENTER);
        TextView result= new TextView(this);


        result.setText("Partida terminada");
        result.setGravity(Gravity.CENTER);
        lll.addView(result);
        cl.addView(lll);
        Intent i= new Intent(GameActivity.this,ResultPage.class);
        i.putExtra("Result",1);
        startActivity(i);

    }
}
