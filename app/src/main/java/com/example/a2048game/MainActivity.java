package com.example.a2048game;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
    MediaPlayer mediaPlayer;
    Switch timerSwitch;
    Switch musicSwitch;

    NumberPicker np;
    int minutes = 0;
    public static final String EXTRA_MINUTES = "com.example.a2048game.MINUTES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle userName = getIntent().getExtras();
        final String nameUser = userName.getString("userName");
        user = (TextView) findViewById(R.id.txtUserName);
        user.setText(nameUser.toString());
        nameGame= (TextView) findViewById(R.id.user);
        timerSwitch = (Switch) findViewById(R.id.timer);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameGame.getText().toString().equals("")) {
                    nameGame.setText(user.getText().toString());
                }

                if (timerSwitch.isChecked()){
                    minutes = np.getValue();
                }
                else{
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



        // NUMBER PICKER
        np = (NumberPicker) findViewById(R.id.np);
        np.setMinValue(1);
        np.setMaxValue(30);
        np.setWrapSelectorWheel(true);


        // MUSIC
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

    /*@Override
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
    }*/

}