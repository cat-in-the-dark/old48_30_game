package com.catinthedark.game.level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 23.08.2014.
 */
public class TilePresetFactory {
    private final List<int[][]> rawTilePresetsEasy;
    private final List<int[][]> rawTilePresetsMedium;
    private final List<int[][]> rawTilePresetsHard;
    private final List<TilePreset> tilePresets;

    public TilePresetFactory() {
        this.rawTilePresetsEasy = new ArrayList<int[][]>() {{
            add(new int[][]{{1, 1, 1}, {2, 2, 2}});
            add(new int[][]{{1, 1, 1}, {2, 2, 2}, {0, 0, 0}});
        }};

        this.rawTilePresetsMedium = new ArrayList<int[][]>();
        this.rawTilePresetsHard = new ArrayList<int[][]>();

        this.tilePresets = new ArrayList<TilePreset>();
        for (int[][] rawTilePreset : rawTilePresetsEasy) {
            tilePresets.add(new TilePreset(rawTilePreset, TilePreset.EASY));
        }
        for (int[][] rawTilePreset : rawTilePresetsMedium) {
            tilePresets.add(new TilePreset(rawTilePreset, TilePreset.MEDIUM));
        }

        for (int[][] rawTilePreset : rawTilePresetsHard) {
            tilePresets.add(new TilePreset(rawTilePreset, TilePreset.HARD));
        }
    }

    public TilePreset build(){

        return null;
    }
}
