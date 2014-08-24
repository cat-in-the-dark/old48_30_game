package com.catinthedark.game.level;

import com.catinthedark.game.Constants;

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

        this.rawTilePresetsMedium = new ArrayList<int[][]>() {{
            add(new int[][]{{1, 1, 1}, {2, 2, 2}});
            add(new int[][]{{1, 1, 1}, {2, 2, 2}, {0, 0, 0}});
        }};
        this.rawTilePresetsHard = new ArrayList<int[][]>() {{
            add(new int[][]{{1, 1, 1}, {2, 2, 2}});
            add(new int[][]{{1, 1, 1}, {2, 2, 2}, {0, 0, 0}});
        }};

        this.tilePresets = new HashMap<Integer, List<TilePreset>>();
        this.tilePresets.put(Constants.EASY, new ArrayList<TilePreset>());
        this.tilePresets.put(Constants.MEDIUM, new ArrayList<TilePreset>());
        this.tilePresets.put(Constants.HARD, new ArrayList<TilePreset>());

        for (int[][] rawTilePreset : rawTilePresetsEasy) {
            tilePresets.get(Constants.EASY).add(new TilePreset(rawTilePreset, Constants.EASY));
        }

        for (int[][] rawTilePreset : rawTilePresetsMedium) {
            tilePresets.get(Constants.MEDIUM).add(new TilePreset(rawTilePreset, Constants.MEDIUM));
        }

        for (int[][] rawTilePreset : rawTilePresetsHard) {
            tilePresets.get(Constants.HARD).add(new TilePreset(rawTilePreset, Constants.HARD));
        }
    }

    public TilePreset build(int difficult){
        if (tilePresets.get(difficult).size() == 0) {
            throw new RuntimeException("There is no tile preset for this difficult " + Integer.toString(difficult));
        }

        Random random = new Random();
        int i = random.nextInt(tilePresets.get(difficult).size());
        return tilePresets.get(difficult).get(i);
    }
}
