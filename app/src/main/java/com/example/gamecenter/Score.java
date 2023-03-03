package com.example.gamecenter;

/**
 * Class score, saves all variables for a score
 */
public class Score {

    private String user;
    private String game;
    private String mode;
    private int score;

    public Score(String user, String game, String mode, int score){
        this.user = user;
        this.game = game;
        this.mode = mode;
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public String getGameName() {
        return game;
    }

    public String getMode() {
        return mode;
    }

    public int getScore() {
        return score;
    }
}
