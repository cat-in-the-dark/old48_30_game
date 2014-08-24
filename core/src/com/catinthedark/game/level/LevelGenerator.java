package com.catinthedark.game.level;

import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.catinthedark.game.Constants;

/**
 * Created by Ilya on 23.08.2014.
 */
public class LevelGenerator {
	private final TilePresetFactory factory;

	public LevelGenerator() {
		this.factory = new TilePresetFactory();
	}

	public void generateLevel(Level level) {
		TilePreset preset = factory.build(level.difficult);
		level.tiles.addAll(preset.tiles);
	}

	public void updateLevel(Level level, Camera camera) {
		List<Tile> tiles = level.tiles;
		float cameraPosition = camera.position.x;
		float levelRightEdge = cameraPosition
				+ camera.viewportWidth / 2f;

		if (tiles.get(tiles.size() - 1).getX() * Constants.tileSize < levelRightEdge) {
			TilePreset preset = factory.build(level.difficult);
			tiles.addAll(preset.tiles);
		}
	}
}
