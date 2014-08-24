package com.catinthedark.game.level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

import entity.Block;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Level {
	public List<Tile> tiles;
	public final int difficult;
	private World world;
	private List<Block> blockList;

	private Block createBlock(int x) {
		PolygonShape blockShape = new PolygonShape();
		blockShape.setAsBox(Constants.BLOCK_WIDTH / 2,
				Constants.BLOCK_HEIGHT / 2);
		PhysicsModel blockModel = new PhysicsModel(world, x, 0, blockShape,
				true, BodyDef.BodyType.StaticBody, 1.0f);
		return new Block(blockModel);
	}

	public Level(Config conf, int difficult) {
		world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
		blockList = new ArrayList<Block>();
		for (int i = 0; i < conf.VIEW_PORT_WIDTH; i++)
			blockList.add(createBlock(i));

		this.difficult = difficult;
		this.tiles = new LinkedList<Tile>();
	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public World getWorld() {
		return world;
	}

	public void render(float delta, SpriteBatch batch) {
		renderTiles(delta, batch);
	}

	private void renderTiles(float delta, SpriteBatch batch) {
	}
}
