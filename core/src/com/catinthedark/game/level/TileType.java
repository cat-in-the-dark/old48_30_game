package com.catinthedark.game.level;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by Ilya on 23.08.2014.
 */
public enum TileType {
    UNDERGROUND(true, "underground", 0),
    GRASS(true, "grass", 1),
    GRASS_SHADOW(false, "grass_shadow", 2),
    GRASS_SLOPE_LEFT(true, "grass_slope_left", 3),
    GRASS_SLOPE_LEFT_SHADOW(false, "grass_slope_left_shadow", 4),
    GRASS_SLOPE_RIGHT(true, "grass_slope_right", 5),
    GRASS_SLOPE_RIGHT_SHADOW(false, "grass_slope_right_shadow", 6),
    EMPTY(false, "empty", 7);

    private static final Map<Integer, TileType> MAP = ImmutableMap.<Integer, TileType>builder()
            .put(0, UNDERGROUND)
            .put(1, GRASS)
            .put(2, GRASS_SHADOW)
            .put(3, GRASS_SLOPE_LEFT)
            .put(4, GRASS_SLOPE_LEFT_SHADOW)
            .put(5, GRASS_SLOPE_RIGHT)
            .put(6, GRASS_SLOPE_RIGHT_SHADOW)
            .put(7, EMPTY)
            .build();

    private final boolean collidable;
    private final String objectName;
    private final int objectId;

    private TileType(boolean collidable, String objectName, int objectId) {
        this.collidable = collidable;
        this.objectName = objectName;
        this.objectId = objectId;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getObjectId() {
        return objectId;
    }

    public static TileType getById(int id) {
        return MAP.get(id);
    }
}
