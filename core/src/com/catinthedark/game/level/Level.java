package com.catinthedark.game.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Constants;

/**
 * Created by kirill on 23.08.14.
 */


public class Level {

    private World world;
    public Level() {
        world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
    }

    public World getWorld() {
        return world;
    }
}
