package entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

import java.awt.*;

public class Player {
	private DirectionX dirX;
	private DirectionY dirY;

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
        getBody().applyLinearImpulse(Constants.jumpImpulse, getBody().getPosition(), true);
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
