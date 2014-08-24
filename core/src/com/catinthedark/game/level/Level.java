package com.catinthedark.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.physics.PhysicsModel;
import entity.Block;
import entity.Entity;
import entity.MushroomedCrab;

import java.util.*;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Level {
	public List<Tile> tiles;
	public final int difficult;
	private World world;
	private List<Block> blockList;
    private Map<Class, List<Entity>> entities = new HashMap<Class, List<Entity>>();
    private Class[] entities_classes = {MushroomedCrab.class};

    private final SpriteBatch batch;
    private final OrthographicCamera camera;

	private Block createBlock(int x) {
		PolygonShape blockShape = new PolygonShape();
		blockShape.setAsBox(Constants.BLOCK_WIDTH / 2,
				Constants.BLOCK_HEIGHT / 2);
		PhysicsModel blockModel = new PhysicsModel(world, x, 0, blockShape,
				true, BodyDef.BodyType.StaticBody, 1.0f);
		return new Block(blockModel);
	}

	public Level(Config conf, int difficult, OrthographicCamera camera) {
        batch = new SpriteBatch();
        this.camera = camera;
		world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
		blockList = new ArrayList<Block>();
		for (int i = 0; i < conf.VIEW_PORT_WIDTH; i++)
			blockList.add(createBlock(i));

		this.difficult = difficult;
		this.tiles = new LinkedList<Tile>();

        for (Class klass : entities_classes) {
            entities.put(klass, new ArrayList<Entity>());
        }
	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public World getWorld() {
		return world;
	}

	public void render(float delta) {
		renderTiles(delta);
	}

	private void renderTiles(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Tile tile : tiles) {
            TextureRegion region;
            switch (tile.type) {
                case GRASS:
                    region = Assets.textures.grass;
                    break;
                case GRASS_SHADOW:
                    region = Assets.textures.grass_shadow;
                    break;
                case UNDERGROUND:
                    region = Assets.textures.underground;
                    break;
                case GRASS_SLOPE_LEFT:
                    region = Assets.textures.grass_slope_slope;
                    break;
                case GRASS_SLOPE_LEFT_SHADOW:
                    region = Assets.textures.grass_slope_left_shadow;
                    break;
                case GRASS_SLOPE_RIGHT:
                    region = Assets.textures.grass_slope_right;
                    break;
                case GRASS_SLOPE_RIGHT_SHADOW:
                    region = Assets.textures.grass_slope_right_shadow;
                    break;
                default:
                    region = Assets.textures.empty;
                    break;
            }
            batch.draw(region, tile.getX() * 32, tile.getY() * 32);
        }
        batch.end();
	}
}
