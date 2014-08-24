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
	private long attackBeganAt;
	private float stateTime;
	private boolean isStay = true;
	private boolean isMoving;
	public int healt = 100;

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
	public void shot() {

	}

	@Override
	public float getStateTime() {
		return stateTime;
	}

	public boolean isMoving() {
		return isMoving;
	}

	@Override
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	@Override
	public void update(float delta, boolean isStay) {
		stateTime += delta;
		this.isStay = isStay;
	}

	@Override
	public void update(float delta) {
		stateTime += delta;
	}
}
