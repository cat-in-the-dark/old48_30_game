package com.catinthedark.game.level;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.catinthedark.game.physics.PhysicsModel;

import entity.Bullet;
import entity.DirectionX;
import entity.Mushroom;
import entity.MushroomedCrab;
import entity.Player;

/**
 * Created by Ilya on 24.08.2014.
 */
public class AIManager {
	public void update(Level level, Player player) {
		List<MushroomedCrab> entities = level.getCrabs();
		for (MushroomedCrab crab : entities) {

			if (crab.getBody().getPosition().x > player.getBody().getPosition().x)
				crab.dirX = DirectionX.LEFT;
			else
				crab.dirX = DirectionX.RIGHT;

			// move
			crab.getBody().applyLinearImpulse(
					new Vector2(crab.getDirX() == DirectionX.RIGHT ? 0.5f
							: -0.5f, 0.1f),
					new Vector2(0, 0), true);

			if (crab.canShot()) {
				Bullet bullet = crab.shot();

				CircleShape mushShape = new CircleShape();
				mushShape.setRadius(0.25f);
				float bulletPosX;
				if (crab.getDirX() == DirectionX.RIGHT)
					bulletPosX = crab.getBody().getPosition().x + 0.6f;
				else
					bulletPosX = crab.getBody().getPosition().x - 0.6f;

				PhysicsModel model = new PhysicsModel(level.getWorld(),
						bulletPosX, crab.getBody()
								.getPosition().y,
						mushShape,
						true, BodyDef.BodyType.DynamicBody, 0.1f);

				((Mushroom) bullet).model = model;
				model.getBody().applyLinearImpulse(
						crab.getDirX() == DirectionX.RIGHT ? 1 : -1, 0, 0, 0,
						true);
				level.addBullet(bullet);
			}
		}
	}
}
