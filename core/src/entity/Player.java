package entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

public class Player {
	private DirectionX dirX;
	private DirectionY dirY;
	private long attackBeganAt;

	private PhysicsModel model;

	public Body getBody() {
		return model.getBody();
	}

	public Player(PhysicsModel model) {
		this.model = model;
	}

	public DirectionX getDirX() {
		return dirX;
	}

	public DirectionY getDirY() {
		return dirY;
	}

	public void moveLeft() {
		dirX = DirectionX.LEFT;
	}

	public void moveRight() {
		dirX = DirectionX.RIGHT;

	}

	public void jump() {
		getBody().applyLinearImpulse(Constants.jumpImpulse,
				getBody().getPosition(), true);
	}

	public boolean isInAttack() {
		return System.currentTimeMillis() - attackBeganAt < 1400;
	}

	public void shot() {
		long now = System.currentTimeMillis();
		if (now - attackBeganAt > 1400) {
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

}
