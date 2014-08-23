package com.catinthedark.game.level;

import com.catinthedark.game.Constants;

import java.util.List;

/**
 * Created by Ilya on 23.08.2014.
 */
public class LevelGenerator {
    private static LevelGenerator instance = new LevelGenerator();
    private TilePresetFactory factory;

    public static LevelGenerator getInstance() {
        return instance;
    }

    private LevelGenerator() {
        this.factory = new TilePresetFactory();
    }

    public void generateLevel(Level level) {
        List<Tile> tiles = level.tiles;
        float cameraPosition = level.gameScreen.getCamera().position.x;
        float levelRightEdge = cameraPosition + level.gameScreen.getCamera().viewportWidth / 2f;
        if (tiles.get(tiles.size()).getX() * Constants.tileSize < levelRightEdge) {
            TilePreset preset = factory.build(level.difficult);
            tiles.addAll(preset.tiles);
        }
    }
}
