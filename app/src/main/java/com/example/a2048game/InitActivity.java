package com.example.a2048game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class InitActivity extends AppCompatActivity {

    public static Save database = new Save();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        final TextView user = findViewById(R.id.txtUser);
        Button bntSign = findViewById(R.id.btn_sign);
        Button bntNew = findViewById((R.id.btnNew));
        bntSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!user.getText().toString().equals("")) {
                    Intent nextView = new Intent(InitActivity.this, MainActivity.class);
                    nextView.putExtra("userName", user.getText().toString());
                    startActivity(nextView);
                }else{
                    TextView info = findViewById(R.id.textInformation);
                    info.setText("Debe ingresar el nombre de usuario");
                }
            }
        });

        bntNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!user.getText().toString().equals("")) {
                    User u = new User();
                    u.setName(user.getText().toString());
                    u.setHighscore(0);
                    u.setHighscoreBomb(0);
                    database.saveUser(u);
                    Intent nextView = new Intent(InitActivity.this,MainActivity.class);
                    nextView.putExtra("userName",user.getText().toString());
                    startActivity(nextView);

                }else{
                    TextView info = findViewById(R.id.textInformation);
                    info.setText("Debe ingresar un nuevo nombre de usuario");
                }
            }
        });


    }
}