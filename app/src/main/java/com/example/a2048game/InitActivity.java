package com.example.a2048game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InitActivity extends AppCompatActivity {
    private DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");
    public static Save database = new Save();
    public boolean exist;
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

                    userDatabase.orderByChild("name").equalTo(user.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean findUserExist;
                            if(dataSnapshot.exists()){
                                Intent nextView = new Intent(InitActivity.this, MainActivity.class);
                                nextView.putExtra("userName", user.getText().toString());
                                startActivity(nextView);
                            }else{
                                TextView info = findViewById(R.id.textInformation);
                                info.setText("Usuario incorrecto");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

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

                    userDatabase.orderByChild("name").equalTo(user.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean findUserExist;
                            if(dataSnapshot.exists()){
                                TextView info = findViewById(R.id.textInformation);
                                info.setText("Nombre de usuario incorrecto inténtelo nuevamente");
                            }else{
                                User u = new User();
                                u.setName(user.getText().toString());
                                u.setHighscore(0);
                                u.setHighscoreBomb(0);
                                database.saveUser(u);
                                Intent nextView = new Intent(InitActivity.this,MainActivity.class);
                                nextView.putExtra("userName",user.getText().toString());
                                startActivity(nextView);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }else{
                    TextView info = findViewById(R.id.textInformation);
                    info.setText("Debe ingresar un nuevo nombre de usuario");
                }
            }
        });
    }

}