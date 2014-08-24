package com.catinthedark.game.level;

import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
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

	public LevelGenerator(Config conf) {
		this.factory = new TilePresetFactory();
		this.conf = conf;
	}

	private MushroomedCrab createCrub(Level level, float posX, float posY) {
		CircleShape crubShape = new CircleShape();
		crubShape.setRadius(Constants.CRUB_WIDTH / 2);
		PhysicsModel crubModel = new PhysicsModel(level.getWorld(), posX
				/ conf.UNIT_SIZE, posY / conf.UNIT_SIZE,
				crubShape,
				true, BodyDef.BodyType.DynamicBody, 0.1f);
		return new MushroomedCrab(crubModel);
	}

	public void generateLevel(Level level) {
		TilePreset preset = factory.build(level.difficult);
		level.tiles.addAll(preset.tiles);
		level.addEntity(createCrub(level, 200, 100));
		level.addEntity(createCrub(level, 250, 100));
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
