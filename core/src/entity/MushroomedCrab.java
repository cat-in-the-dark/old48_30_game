package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

/**
 * Created by Ilya on 24.08.2014.
 */
public class MushroomedCrab implements Entity {
	private DirectionX dirX = DirectionX.RIGHT;
	private DirectionY dirY;
	private float lastShutTime = 0f;
	private float stateTime;
	private boolean isMoving;
	public int healt = 100;
	private boolean shutting = false;
	private boolean canShut = false;

	private PhysicsModel model;

	public MushroomedCrab(PhysicsModel model) {
		this.model = model;
	}

	@Override
	public Body getBody() {
		return model.getBody();
	}

	@Override
	public PhysicsModel getModel() {
		return model;
	}

	@Override
	public DirectionX getDirX() {
		return dirX;
	}

	@Override
	public DirectionY getDirY() {
		return dirY;
	}

	@Override
	public void moveRight() {
		dirX = DirectionX.RIGHT;
		getBody().applyLinearImpulse(Constants.WALKING_FORCE_RIGHT,
				new Vector2(0f, 0f), true);
	}

	@Override
	public void moveLeft() {
		dirX = DirectionX.LEFT;
		getBody().applyLinearImpulse(Constants.WALKING_FORCE_LEFT,
				new Vector2(0f, 0f), true);
	}

	@Override
	public Bullet shot() {
		System.out.println("Shut");
		lastShutTime = stateTime;
		shutting = true;
		canShut = false;

		if (getDirX() == DirectionX.RIGHT)
			return new Mushroom(DirectionX.RIGHT,
					DirectionY.CROSSHAIR_MIDDLE);
		else
			return new Mushroom(DirectionX.LEFT,
					DirectionY.CROSSHAIR_MIDDLE);
	}

	@Override
	public float getStateTime() {
		return stateTime;
	}

	public boolean isMoving() {
		return isMoving;
	}

	@Override
	public boolean isShutting() {
		return shutting;
	}

	public boolean canShot() {
		return canShut;
	}

	@Override
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
		if (stateTime - lastShutTime > Constants.MUSHROOMED_CRAB_SHUT_DELAY)
			canShut = true;
		if (stateTime - lastShutTime > Constants.MUSHROOMED_CRAB_SHUT_TIME)
			shutting = false;
	}
}
