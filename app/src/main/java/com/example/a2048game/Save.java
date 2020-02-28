package com.example.a2048game;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Save extends AppCompatActivity {

    private DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference rootChildUser = myDatabase.child("users");
    private DatabaseReference rootChildGame = myDatabase.child("games");
    public static InitActivity init = new InitActivity();
    public void saveGame(Game g){
        saveGameDatabase(g);
    }

    private void saveGameDatabase(Game g){
        rootChildGame.push().setValue(g);
    }

    public void saveUser(User u){
        saveUserDatabase(u);
    }

    private void saveUserDatabase(User u){

        //myDatabase.child("name").updateChildren(dataUser);
        rootChildUser.push().setValue(u);
    }

    public void updatehighscoreBomb(String name,int highscoreBomb){
        updatehighscoreBombDatabase(name,highscoreBomb);
    }

    private void  updatehighscoreBombDatabase(String name,int highscoreBom){
        Map<String, Object>dataUser = new HashMap<>();
        dataUser.put("name", name);
        dataUser.put("highscoreBomb", highscoreBom);
        myDatabase.child(name).updateChildren(dataUser);
    }

    public void updatehighscore(String name,int highscore){
        updatehighscoreDatabase(name,highscore);
    }

    private void  updatehighscoreDatabase(String name,int highscore){
        Map<String, Object>dataUser = new HashMap<>();
        dataUser.put("name", name);
        dataUser.put("highscore", highscore);
        myDatabase.child(name).updateChildren(dataUser);
    }

    public void findUser(String userName){
        find(userName);
    }

    private void find(String userName){
      /*  userDatabase.orderByChild("name").equalTo(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean findUserExist;
                if(dataSnapshot.exists()){
                    findUserExist = true;
                }else{
                    findUserExist = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
*/
    }

}
