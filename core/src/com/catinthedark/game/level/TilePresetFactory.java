package com.catinthedark.game.level;

import java.util.*;

/**
 * Created by Ilya on 23.08.2014.
 */
public class TilePresetFactory {
    private final List<int[][]> rawTilePresetsEasy;
    private final List<int[][]> rawTilePresetsMedium;
    private final List<int[][]> rawTilePresetsHard;

    private final Map<Integer, List<TilePreset>> tilePresets;

    public TilePresetFactory() {
        this.rawTilePresetsEasy = new ArrayList<int[][]>() {{
            add(new int[][]{{1, 1, 1}, {2, 2, 2}});
            add(new int[][]{{1, 1, 1}, {2, 2, 2}, {0, 0, 0}});
        }};

        this.rawTilePresetsMedium = new ArrayList<int[][]>();
        this.rawTilePresetsHard = new ArrayList<int[][]>();

        this.tilePresets = new HashMap<Integer, List<TilePreset>>();
        this.tilePresets.put(TilePreset.EASY, new ArrayList<TilePreset>());
        this.tilePresets.put(TilePreset.MEDIUM, new ArrayList<TilePreset>());
        this.tilePresets.put(TilePreset.HARD, new ArrayList<TilePreset>());

        for (int[][] rawTilePreset : rawTilePresetsEasy) {
            tilePresets.get(TilePreset.EASY).add(new TilePreset(rawTilePreset, TilePreset.EASY));
        }

        for (int[][] rawTilePreset : rawTilePresetsMedium) {
            tilePresets.get(TilePreset.MEDIUM).add(new TilePreset(rawTilePreset, TilePreset.MEDIUM));
        }

        for (int[][] rawTilePreset : rawTilePresetsHard) {
            tilePresets.get(TilePreset.HARD).add(new TilePreset(rawTilePreset, TilePreset.HARD));
        }
    }

    public TilePreset build(int difficult){
        Random random = new Random();
        int i = random.nextInt(tilePresets.get(difficult).size() + 1);
        return tilePresets.get(difficult).get(i);
    }
}
