package com.catinthedark.game;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	public static final int tileSize = 32;

	public static final float PLAYER_WIDTH = 2.0f;
	public static final float PLAYER_HEIGHT = 2.0f;

    public static final float MUSHROOMED_CRAB_WIDTH = 2.0f;
    public static final float MUSHROOMED_CRAB_HEIGHT = 2.0f;

	public static final float BLOCK_WIDTH = 1.0f;
	public static final float BLOCK_HEIGHT = 1.0f;

	public static final int WORLD_GRAVITY = -30;

	public static final Vector2 JUMP_IMPULSE = new Vector2(0f, 7.0f);
	public static final float WALKING_FORCE = 1.0f;
	public static final int FRICTION = 1;
	public static final Vector2 WALKING_FORCE_RIGHT = new Vector2(
			WALKING_FORCE, 0f);
	public static final Vector2 WALKING_FORCE_LEFT = new Vector2(-1
			* WALKING_FORCE, 0f);

	public static final float ANIMATION_SPEED = 0.1f;
}
