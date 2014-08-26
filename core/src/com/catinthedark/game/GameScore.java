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
    private float walkedDistance;

    public static GameScore getInstance() {
        return ourInstance;
    }

    private GameScore() {
        resetScore();
    }

    public int getScore() {
        return score;
    }

    public void priceCrab() {
        this.score += Constants.CRAB_SCORE;
    }

    public void resetScore() {
        setHealth(Constants.START_HEALTH);
        this.score = 0;
        this.level = Constants.EASY;
        this.meters = 0;
        this.walkedDistance = 0f;
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

    public int getLevel() {
        return level;
    }

    public void incDistance() {
        walkedDistance += Constants.MAIN_CAMERA_SPEED;
        this.meters = ((int) ((walkedDistance * 100) / Constants.DISTANCE_MAX_EASY));
    }

    public boolean isWalked() {
        return walkedDistance >= Constants.DISTANCE_MAX_EASY;
    }
}
