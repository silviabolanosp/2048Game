package com.example.a2048game;

public class User {
    String name;
    int highscore;
    int highscoreBomb;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getHighscoreBomb() {
        return highscoreBomb;
    }

    public void setHighscoreBomb(int highscoreBomb) {
        this.highscoreBomb = highscoreBomb;
    }
}
