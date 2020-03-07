package com.example.a2048game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    ConstraintLayout layout;
    TextView title;
    Bundle mode;
    boolean darkMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        layout = findViewById(R.id.resultPage);
        mode = getIntent().getExtras();
        title = findViewById(R.id.titleScore);
        darkMode = mode.getBoolean("mode");
        userName = getIntent().getExtras();
        final String nameUser = userName.getString("user");
        changeMode();
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
                nextView.putExtra("mode",darkMode);
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

    private void changeMode() {
        if (darkMode == true){
            layout.setBackgroundColor(Color.parseColor("#424242"));
            title.setTextColor(Color.parseColor("#FBFEF9"));
        } else {
            layout.setBackgroundColor(Color.parseColor("#F7F7EA"));
            title.setTextColor(Color.parseColor("#000000"));
        }
    }
}
