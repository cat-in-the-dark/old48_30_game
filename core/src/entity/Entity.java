package entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.physics.PhysicsModel;

/**
 * Created by Ilya on 24.08.2014.
 */
public interface Entity {
    public Body getBody();
    public PhysicsModel getModel();
    public DirectionX getDirX();
    public DirectionY getDirY();
    public void moveRight();
    public void moveLeft();
    public void shot();
    public float getStateTime();
    public void update(float delta, boolean isStay);
    public void update(float delta);
    public boolean isMoving();
    public void setMoving(boolean isMoving);
}

