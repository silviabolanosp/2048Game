package com.example.a2048game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView username;

    //Referencia a la base de datos
    DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    DatabaseReference rootChild = myDatabase.child("users");
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        username= (TextView) findViewById(R.id.user);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                Map<String, Object>dataUser = new HashMap<>();
                dataUser.put("users", name);
                myDatabase.child("name").updateChildren(dataUser);
                rootChild.child("natasha").setValue("Kristel Guzman");
                Intent nextView = new Intent(MainActivity.this,GameActivity.class);
                startActivity(nextView);

            }
        });
    }


    @Override
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
    }
}
