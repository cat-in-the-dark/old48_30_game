package com.catinthedark.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Tile {
    public final TileType type;
    private float xTilePos;
    private float yTilePos;

    /**
     *
     * @param xTilePos
     * @param yTilePos
     * @param type
     */
    public Tile(int xTilePos, int yTilePos, TileType type) {
        if (type == null) {
            throw new RuntimeException("Cannot create tile without type");
        }

        this.xTilePos = xTilePos;
        this.yTilePos = yTilePos;
        this.type = type;
    }

    public void render(float delta, SpriteBatch batch, float x, float y){
        // TODO
    }
}
