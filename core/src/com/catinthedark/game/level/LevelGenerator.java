package com.catinthedark.game.level;

import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

import entity.MushroomedCrab;

/**
 * Created by Ilya on 23.08.2014.
 */
public class LevelGenerator {
	private final TilePresetFactory factory;
	private final Config conf;
	private float lastPresetX = 0;

	public LevelGenerator(Config conf, Level level) {
		this.factory = new TilePresetFactory(level.getWorld());
		this.conf = conf;
	}

	public void generateLevel(Level level) {
		// level.addEntity(createCrub(level, 200, 100));
		// level.addEntity(createCrub(level, 250, 100));

		LevelPart part = factory.build(level.difficult, lastPresetX, 0, false);
		List<Tile> tilesList = part.tiles;
		level.tiles.addAll(tilesList);

//		for (MushroomedCrab crab : part.crabs)
//			level.addEntity(crab);

		lastPresetX = tilesList.get(tilesList.size() - 1).getX()
				+ Constants.BLOCK_WIDTH;
	}

	public void updateLevel(Level level, Camera camera) {
		List<Tile> tiles = level.tiles;
		float cameraPosition = camera.position.x;
		float levelRightEdge = cameraPosition
				+ camera.viewportWidth / 2f;

		if (tiles.get(tiles.size() - 1).getX() * Constants.tileSize < levelRightEdge) {
			LevelPart part = factory.build(level.difficult, lastPresetX, 0,
					true);
			List<Tile> tileList = part.tiles;
			tiles.addAll(tileList);

			for (MushroomedCrab crab : part.crabs)
				level.addEntity(crab);

			lastPresetX = tileList.get(tileList.size() - 1).getX()
					+ Constants.BLOCK_WIDTH;
		}
	}
}
