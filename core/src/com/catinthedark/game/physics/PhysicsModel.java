package com.catinthedark.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by kirill on 23.08.14.
 */
public class PhysicsModel {
    private final Body body;
    private final Shape shape;
    private final Fixture fixture;

    public PhysicsModel(World world, float x, float y, Shape shape, boolean fixedRotation, BodyDef.BodyType type, float density) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.type = type;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        this.shape = shape;
        fixture = body.createFixture(this.shape, density);
        this.shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    public Shape getShape() {
        return shape;
    }

    public Fixture getFixture() {
        return fixture;
    }
}
