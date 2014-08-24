package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.catinthedark.game.physics.PhysicsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 24.08.14.
 */
public class Cable {
    private List<Body> bodyList;

    private List<Joint> jointList;

    public final float segmentLength;
    public final float segmentThick;

    public Cable(World world, Vector2 startPos, float segmentLength, int segmentCount) {
        this.segmentLength = segmentLength;
        this.segmentThick = 0.5f;

        bodyList = new ArrayList<Body>(segmentCount);
        jointList = new ArrayList<Joint>(segmentCount + 1);  // we need joints at ends of cable;

        for (int i = 0; i < segmentCount; i++) {
            CircleShape segmentShape = new CircleShape();
            segmentShape.setRadius(segmentThick / 2);

            PhysicsModel segmentPhysicsModel = new PhysicsModel(world, (startPos.x - i * segmentLength), startPos.y,
                    segmentShape, false, BodyDef.BodyType.DynamicBody, 0.01f);
            Block segment = new Block(segmentPhysicsModel);
            bodyList.add(segment.getBody());

            if (i > 0) {
                RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
                revoluteJointDef.bodyA = segmentPhysicsModel.getBody();
                revoluteJointDef.bodyB = bodyList.get(i - 1);
                revoluteJointDef.collideConnected = false;
                revoluteJointDef.localAnchorA.set(0, segmentLength);

                Joint joint = world.createJoint(revoluteJointDef);
            }

            if (i == segmentCount - 1) {
                segment.getBody().setGravityScale(-0.5f * (segmentCount - 1));
            }
        }
    }

    public List<Body> getBodyList() {
        return bodyList;
    }

    public List<Joint> getJointList() {
        return jointList;
    }
}
