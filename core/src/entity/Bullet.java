package entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.physics.PhysicsModel;

/**
 * Created by Ilya on 24.08.2014.
 */
public interface Bullet {
    public Body getBody();
    public PhysicsModel getModel();
    public DirectionX getDirX();
    public DirectionY getDirY();
    public void move();
    public void update(float delta);
}
