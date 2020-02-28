package com.example.a2048game;

import android.content.Intent;
import android.os.Bundle;;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ResultPage extends AppCompatActivity {
    Button btnback;
    private List<Game> listGames = new ArrayList<Game>();
    ArrayAdapter<Game> arrayAdapter;
    DatabaseReference myDatabase;
    ListView lisG;
    Bundle userName;
    String name;
    private TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        userName = getIntent().getExtras();
        final String nameUser = userName.getString("user");
        user = findViewById(R.id.txtUserName);
        user.setText(nameUser.toString());
        lisG = findViewById(R.id.list);
        databaseIni();
        name = userName.getString("user");
        listGames();
        btnback = findViewById(R.id.btnCancel);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextView = new Intent(ResultPage.this,MainActivity.class);
                name = userName.getString("user");
                nextView.putExtra("userName", name);
                startActivity(nextView);
            }
        });

    }

    private void databaseIni() {
        myDatabase = FirebaseDatabase.getInstance().getReference("games");
    }

    private void listGames() {
        myDatabase.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listGames.clear();
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    Game g = obj.getValue(Game.class);
                   if(g.getUser().equals(name)){
                        listGames.add(g);
                        arrayAdapter = new ArrayAdapter<Game>(ResultPage.this, android.R.layout.simple_list_item_1, listGames);
                        lisG.setAdapter(arrayAdapter);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
