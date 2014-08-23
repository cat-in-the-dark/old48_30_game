package com.catinthedark.game.level;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by Ilya on 23.08.2014.
 */
public enum TileType {
    UNDERGROUND(false, "underground", 0),
    GRASS(true, "grass", 1),
    GRASS_SHADOW(false, "grass_shadow", 2);

    private static final Map<Integer, TileType> MAP = ImmutableMap.<Integer, TileType>builder()
            .put(0, UNDERGROUND)
            .put(1, GRASS)
            .put(2, GRASS_SHADOW)
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
