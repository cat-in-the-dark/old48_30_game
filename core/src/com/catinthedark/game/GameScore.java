package com.catinthedark.game;

/**
 * Created by Ilya on 24.08.2014.
 */
public class GameScore {
    private static GameScore ourInstance = new GameScore();
    private int score;
    private int meters;
    private int health;
    private int level;

    public static GameScore getInstance() {
        return ourInstance;
    }

    private GameScore() {
        resetScore();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void priceCrab() {
        this.score += Constants.CRAB_SCORE;
    }

    public void resetScore() {
        setScore(0);
        setMeters(0);
        setHealth(Constants.START_HEALTH);
        level = Constants.EASY;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void incHealth() {
        health++;
    }

    public void decHealth() {
        health--;
    }

    public int getMeters() {
        return meters;
    }

    public void setMeters(int meters) {
        this.meters = meters;
    }

    public int getLevel() {
        return level;
    }
}
