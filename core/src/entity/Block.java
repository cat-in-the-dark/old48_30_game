package entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

/**
 * Created by kirill on 23.08.14.
 */
public class Block {

    private PhysicsModel model;

    public Body getBody() {
        return model.getBody();
    }

    public Block(PhysicsModel model) {
        this.model = model;
    }
}
