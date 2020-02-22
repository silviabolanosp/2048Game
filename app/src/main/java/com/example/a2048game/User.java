package com.example.a2048game;

public class User {
    private String name;
    private int highscore;
    private int highscoreBomb;

    public User() {

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
