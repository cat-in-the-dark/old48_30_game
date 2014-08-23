package com.catinthedark.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Constants;
import com.catinthedark.game.screen.impl.GameScreen;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Level {
    public List<Tile> tiles;
    public final int difficult;
    private World world;
    public GameScreen gameScreen;

    public Level(GameScreen gameScreen, int difficult) {
        world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
        this.gameScreen = gameScreen;
        this.difficult = difficult;
        this.tiles = new LinkedList<Tile>();
    }

    public void render(float delta, SpriteBatch batch) {
        LevelGenerator.getInstance().generateLevel(this);

        renderTiles(delta, batch);
    }

    private void renderTiles(float delta, SpriteBatch batch) {
    }


    public World getWorld() {
        return world;
    }
}
