package com.catinthedark.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.game.screen.impl.GameScreen;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Level {
    public List<Tile> tiles;
    public final int difficult;
    public GameScreen gameScreen;

    public Level(GameScreen gameScreen, int difficult) {
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
}
