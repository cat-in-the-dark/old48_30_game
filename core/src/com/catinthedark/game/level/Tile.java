package com.catinthedark.game.level;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Tile {
    public final TileType type;
    private float x;
    private float y;

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

        this.x = xTilePos;
        this.y = yTilePos;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
