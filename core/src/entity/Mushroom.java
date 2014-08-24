package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

/**
 * Created by Ilya on 24.08.2014.
 */
public class Mushroom implements Bullet {
    private final DirectionX dirX;
    private final DirectionY dirY;
    private PhysicsModel model;
    private float stateTime = 0f;

    public Mushroom(DirectionX dirX, DirectionY dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
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
    public void move() {
        switch (getDirX()){
            case RIGHT:
                getBody().applyLinearImpulse(Constants.BULLET_FORCE_RIGHT, new Vector2(0f, 0f), true);
                break;
            case LEFT:
                getBody().applyLinearImpulse(Constants.BULLET_FORCE_LEFT, new Vector2(0f, 0f), true);
                break;
        }
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
    }

    public float getStateTime() {
        return stateTime;
    }
}
