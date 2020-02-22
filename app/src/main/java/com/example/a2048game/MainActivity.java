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

                if (timerSwitch.isChecked())
                    minutes = np.getValue();
                else
                    minutes = 0;

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
                startActivity(scoresView);
            }
        });



        //Get the widgets reference from XML layout
        np = (NumberPicker) findViewById(R.id.np);

        //Set TextView text color
     //   tv.setTextColor(Color.parseColor("#ffd32b3b"));

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
             //   tv.setText("Minutos : " + newVal);
            }
        });

        // MUSIC
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mediaPlayer.start();
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

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
}
