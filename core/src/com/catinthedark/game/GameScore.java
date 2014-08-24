package com.catinthedark.game;

/**
 * Created by Ilya on 24.08.2014.
 */
public class GameScore {
    private static GameScore ourInstance = new GameScore();
    private int score;
    private int wireLength;

    public static GameScore getInstance() {
        return ourInstance;
    }

    private GameScore() {
        resetScore();
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void setWireLength(int wireLength) {
        this.wireLength = wireLength;
    }

    public int getScore() {
        return score;
    }

    public int getWireLength() {
        return wireLength;
    }

    public void priceEnemy() {
        this.score += Constants.ENEMY_PRICE;
    }

    public void resetScore() {
        setScore(0);
        setWireLength(0);
    }
}
