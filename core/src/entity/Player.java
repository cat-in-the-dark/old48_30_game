package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

public class Player {
	private DirectionX dirX;
	private DirectionY dirY;
	private long attackBeganAt;
	private float stateTime;
	private boolean isStay = true;
	private boolean isMooving;

	private PhysicsModel model;

	public Body getBody() {
		return model.getBody();
	}

	public Player(PhysicsModel model) {
		this.model = model;
	}

	public PhysicsModel getModel() {
		return model;
	}

	public DirectionX getDirX() {
		return dirX;
	}

	public DirectionY getDirY() {
		return dirY;
	}

	public float getStateTime() {
		return stateTime;
	}

	public boolean isStay() {
		return isStay;
	}

	public void setStay(boolean isStay) {
		this.isStay = isStay;
	}

	public void moveLeft() {
		dirX = DirectionX.LEFT;
		// getBody().applyForceToCenter(Constants.WALKING_FORCE_LEFT, true);
		getBody().applyLinearImpulse(Constants.WALKING_FORCE_LEFT,
				new Vector2(0f, 0f), true);
	}

	public void moveRight() {
		dirX = DirectionX.RIGHT;
		getBody().applyLinearImpulse(Constants.WALKING_FORCE_RIGHT,
				new Vector2(0f, 0f), true);
		// getBody().applyForceToCenter(Constants.WALKING_FORCE_RIGHT, true);
	}

	public void jump() {
		getBody().applyLinearImpulse(Constants.JUMP_IMPULSE,
				getBody().getPosition(), true);
	}

	public boolean isInAttack() {
		return System.currentTimeMillis() - attackBeganAt < 1000;
	}

	public void shot() {
		long now = System.currentTimeMillis();
		if (now - attackBeganAt > 1000) {
			// new attack
			attackBeganAt = now;
		}

	}

	public void crosshairUp() {
		dirY = DirectionY.CROSSHAIR_UP;
	}

	public void crosshairDown() {
		dirY = DirectionY.CROSSHAIR_DOWN;

	}

	public void crosshairMiddle() {
		dirY = DirectionY.CROSSHAIR_MIDDLE;
	}

	public void update(float delta, boolean isStay) {
		stateTime += delta;
		this.isStay = isStay;
	}

	public void update(float delta) {
		stateTime += delta;
	}

	public boolean isMooving() {
		return isMooving;
	}

	public void setMooving(boolean isMooving) {
		this.isMooving = isMooving;
	}

}
