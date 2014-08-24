package com.catinthedark.game.level;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Tile {
    public final TileType type;
    public float x;
    public float y;

    /**
     *
     * @param xTilePos
     * @param yTilePos
     * @param type
     */
    public Tile(float xTilePos, float yTilePos, TileType type) {
        if (type == null) {
            throw new RuntimeException("Cannot create tile without type");
        }

        this.x = xTilePos;
        this.y = yTilePos;
        this.type = type;
    }

    public Tile(Tile tile) {
        if (tile.type == null) {
            throw new RuntimeException("Cannot create tile without type");
        }

        this.x = tile.getX();
        this.y = tile.getY();
        this.type = tile.type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
