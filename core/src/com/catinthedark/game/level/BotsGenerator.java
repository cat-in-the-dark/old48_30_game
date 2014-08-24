package com.catinthedark.game.level;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

import entity.MushroomedCrab;
import entity.Player;

public class BotsGenerator {
	private final Level level;

	private float lastGen = 0;

	public BotsGenerator(Level level) {
		this.level = level;
	}

	private MushroomedCrab createCrub(Level level, float posX, float posY) {
		PolygonShape crubShape = new PolygonShape();
		crubShape.setAsBox(Constants.CRUB_WIDTH / 2, Constants.CRUB_HEIGHT / 2);
		// crubShape.setRadius(Constants.CRUB_WIDTH / 2);
		PhysicsModel crubModel = new PhysicsModel(level.getWorld(), posX
				/ 32, posY / 32,
				crubShape,
				true, BodyDef.BodyType.DynamicBody, 0.1f);
		MushroomedCrab mush = new MushroomedCrab(crubModel);

		if (new Random().nextInt() % 2 == 0)
			mush.moveRight();
		else
			mush.moveLeft();

		return mush;
	}

	public void step(float delta, Player player) {
		if (lastGen > 3) {
			level.addEntity(createCrub(level,
					player.getBody().getPosition().x * 32
							+ new Random().nextInt(200), 600));
			lastGen = 0;
		}

		lastGen += delta;

	}
}
