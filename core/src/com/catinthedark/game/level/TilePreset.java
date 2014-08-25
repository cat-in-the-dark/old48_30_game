package com.catinthedark.game.level;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ilya on 23.08.2014.
 */
public class TilePreset {
	private final int difficult;
	public final List<Tile> tiles;
	public final List<Vector2[]> botsPositions;

	public TilePreset(int[][] rawTilePresets, int difficult,
			List<Vector2[]> botsPositions) {
		this.difficult = difficult;
		this.botsPositions = botsPositions;
		tiles = new ArrayList<Tile>();
		for (int i = 0; i < rawTilePresets.length; i++) {
			for (int j = 0; j < rawTilePresets[i].length; j++) {
				tiles.add(new Tile(i, j, TileType.getById(rawTilePresets[i][j])));
			}
		}
	}

}
