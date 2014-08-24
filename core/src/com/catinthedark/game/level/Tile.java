package com.catinthedark.game.level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.catinthedark.game.Constants;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Tile {
    public final TileType type;
    public float x;
    public float y;
    public Body body;
    public Shape shape;

    /**
     *
     * @param xTilePos
     * @param yTilePos
     * @param type
     */
    public Tile(float xTilePos, float yTilePos, TileType type) {
        if (type == null) {
            throw new RuntimeException("Cannot create tile without type");
        }

        this.x = xTilePos;
        this.y = yTilePos;
        this.type = type;
    }

    public Tile(Tile tile, float x, float y, World world) {
        if (tile.type == null) {
            throw new RuntimeException("Cannot create tile without type");
        }

        this.x = x;
        this.y = y;

        this.type = tile.type;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x, y);
        this.body = world.createBody(def);

        PolygonShape blockShape;
        Vector2 dots[];
        Fixture fixture;

        switch (this.type) {
            case GRASS:
            case GRASS_SHADOW:
            case UNDERGROUND:
            case GRASS_SLOPE_LEFT_SHADOW:
            case GRASS_SLOPE_RIGHT_SHADOW:
                blockShape = new PolygonShape();
                blockShape.setAsBox(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT / 2);
                this.shape = blockShape;
                fixture = body.createFixture(this.shape, 0);
                break;
            case GRASS_SLOPE_LEFT:
                blockShape = new PolygonShape();
                dots = new Vector2[3];
                dots[0] = new Vector2(0, Constants.BLOCK_HEIGHT);
                dots[1] = new Vector2(Constants.BLOCK_WIDTH, 0);
                dots[2] = new Vector2(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
                blockShape.set(dots);
                this.shape = blockShape;
                fixture = body.createFixture(this.shape, 0);
                break;
            case GRASS_SLOPE_RIGHT:
                blockShape = new PolygonShape();
                dots = new Vector2[3];
                dots[0] = new Vector2(0, 0);
                dots[1] = new Vector2(0, Constants.BLOCK_HEIGHT);
                dots[2] = new Vector2(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
                blockShape.set(dots);
                this.shape = blockShape;
                fixture = body.createFixture(this.shape, 0);
                break;
            default:
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
