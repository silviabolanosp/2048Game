package com.example.a2048game;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.media.MediaPlayer;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView nameGame;
    private TextView user;
    Button btnStart;
    Button btnScores;
    MediaPlayer mediaPlayer;
    Switch timerSwitch;
    Switch colorSwtich;
    NumberPicker np;
    int minutes = 0;
    public static final String EXTRA_MINUTES = "com.example.a2048game.MINUTES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle userName = getIntent().getExtras();
        final String nameUser = userName.getString("userName");
        user = findViewById(R.id.txtUserName);
        user.setText(nameUser.toString());
        nameGame= findViewById(R.id.user);
        timerSwitch = findViewById(R.id.timer);
        colorSwtich = findViewById(R.id.colorSwitch);
        btnStart = findViewById(R.id.btnStart);

        if (colorSwtich.isChecked()) {
            btnStart.setBackgroundColor(Color.parseColor("#fff"));
        }else {
        }

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


                Intent nextView = new Intent(MainActivity.this,GameActivity.class);
                nextView.putExtra("nameGame",nameGame.getText().toString());
                nextView.putExtra("user", user.getText().toString());
                nextView.putExtra(EXTRA_MINUTES, minutes);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

}