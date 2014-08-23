package com.catinthedark.game;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int tileSize = 32;

    public static final float PLAYER_WIDTH = 2.0f;
    public static final float PLAYER_HEIGHT = 2.0f;

    public static final float BLOCK_WIDTH = 1.0f;
    public static final float BLOCK_HEIGHT = 1.0f;

    public static final int WORLD_GRAVITY = -10;

    public static final Vector2 jumpImpulse = new Vector2(0f, 5.0f);
}
