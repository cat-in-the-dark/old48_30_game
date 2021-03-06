package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.physics.PhysicsModel;

public class Player {
	private DirectionX dirX;
	private DirectionY dirY;
	private long attackBeganAt;
	private float stateTime;
	private boolean isOnGround = true;
	private boolean isMooving;
    private boolean isDamaged = false;

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

	public boolean isOnGround() {
		return isOnGround;
	}

	public void setOnGround(boolean isStay) {
		if (isOnGround == false)
			Assets.audios.landing.play();
		isOnGround = isStay;
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
		Assets.audios.jump.play(1.0f);
	}

	public boolean isInAttack() {
		return System.currentTimeMillis() - attackBeganAt < 1300;
	}

	public void shot() {
		long now = System.currentTimeMillis();
		if (now - attackBeganAt > 1300) {
			// new attack
			attackBeganAt = now;
			Assets.audios.shot.play(3.0f);
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

	public void update(float delta) {
		stateTime += delta;
	}

	public boolean isMooving() {
		return isMooving;
	}

	public void setMooving(boolean isMooving) {
		this.isMooving = isMooving;
	}

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
        if (isDamaged) {
            Assets.audios.hit.play();
        }
    }
}
