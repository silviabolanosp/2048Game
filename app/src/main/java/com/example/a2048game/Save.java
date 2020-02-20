package com.example.a2048game;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Save extends AppCompatActivity {

    private DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference rootChildUser = myDatabase.child("users");
    private DatabaseReference rootChildGame = myDatabase.child("games");

    private TextView username;

    public void saveGame(String username, String name, int highscore, String type){
        saveGameDatabase(username, name, highscore, type);
    }

    private void saveGameDatabase(String username, String name, int highscore, String type){

        Map<String, Object> dataGame = new HashMap<>();
        dataGame.put("name", name);
        dataGame.put("highscore", highscore);
        dataGame.put("user", "-M0YttiZWPs57MlAh-tk");
        dataGame.put("type", "normal");
        rootChildGame.child("game").push().setValue(dataGame);
    }

    public void saveUser(String name){
        saveUserDatabase(name);
    }

    private void saveUserDatabase(String name){

        //myDatabase.child("name").updateChildren(dataUser);
        Map<String, Object>dataUser = new HashMap<>();
        dataUser.put("name", name);
        dataUser.put("highscore", "0");
        dataUser.put("highscoreBomb", "0");
        rootChildUser.child("user").push().setValue(dataUser);
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

}
