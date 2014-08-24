package com.catinthedark.game.level;

import java.util.List;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.catinthedark.game.physics.PhysicsModel;

import entity.Bullet;
import entity.Mushroom;
import entity.MushroomedCrab;

/**
 * Created by Ilya on 24.08.2014.
 */
public class AIManager {
	public void update(Level level) {
		List<MushroomedCrab> entities = level.getCrabs();
		for (MushroomedCrab entity : entities) {
			Bullet bullet = entity.shot();
			if (bullet != null) {

				CircleShape playerShape = new CircleShape();
				playerShape.setRadius(0.25f);
				PhysicsModel playerModel = new PhysicsModel(level.getWorld(),
						entity.getBody().getPosition().x + 2, entity.getBody()
								.getPosition().y,
						playerShape,
						true, BodyDef.BodyType.DynamicBody, 0.1f);
				((Mushroom) bullet).model = playerModel;
				playerModel.getBody().applyLinearImpulse(1, 0, 0, 0, true);
				level.addBullet(bullet);
			}
		}
	}
}
