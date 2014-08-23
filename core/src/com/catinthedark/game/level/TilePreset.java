package com.catinthedark.game.level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 23.08.2014.
 */
public class TilePreset {
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    private final int difficult;
    private final List<Tile> tiles;

    public TilePreset(int[][] rawTilePresets, int difficult) {
        this.difficult = difficult;
        tiles = new ArrayList<Tile>();
        for (int i = 0; i < rawTilePresets.length; i++) {
            for (int j = 0; j < rawTilePresets[i].length; j++) {
                tiles.add(new Tile(i ,j , TileType.getById(rawTilePresets[i][j])));
            }
        }
    }
}
