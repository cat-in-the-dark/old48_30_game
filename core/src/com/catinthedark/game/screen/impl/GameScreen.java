package com.catinthedark.game.screen.impl;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;
import entity.Block;
import render.BlocksRender;
import render.HudRenderer;
import render.LevelRender;
import render.PlayerRender;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.game.Config;
import com.catinthedark.game.hud.Hud;
import com.catinthedark.game.screen.ResizableScreen;
import com.catinthedark.game.level.Level;

import entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ResizableScreen {

	private final Camera camera;
	private final Hud hud;
	private final HudRenderer hudRenderer;
	private final PlayerRender playerRenderer;
    private final LevelRender levelRender;
    private final BlocksRender blocksRender;
    private Player player;
    private Level level;
    private SpriteBatch batch;
    private List<Block> blockList;

	public GameScreen(Config conf) {
		super(conf);

		this.camera = new OrthographicCamera(conf.VIEW_PORT_WIDTH,
				conf.VIEW_PORT_HEIGHT);
		this.camera.update();

		hud = new Hud(10);
		hudRenderer = new HudRenderer(conf);
		hud.addMeters(0);

		playerRenderer = new PlayerRender(conf);

        blocksRender = new BlocksRender(conf);

        level = new Level();
        levelRender = new LevelRender();

        player = createPlayer(level.getWorld());

		player.moveRight();
		player.crosshairMiddle();
        batch = new SpriteBatch();

        blockList = new ArrayList<Block>();
        blockList.add(createBlock(level.getWorld()));
	}

    private Player createPlayer(World world) {
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(Constants.PLAYER_WIDTH / 2, Constants.PLAYER_HEIGHT / 2);
        PhysicsModel playerModel = new PhysicsModel(world, 0, 5, playerShape, true, BodyDef.BodyType.DynamicBody, 0.1f);
        return new Player(playerModel);
    }

    private Block createBlock(World world) {
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT / 2);
        PhysicsModel blockModel = new PhysicsModel(world, 0, 0, blockShape, true, BodyDef.BodyType.StaticBody, 0.1f);
        return new Block(blockModel);
    }

	@Override
	public void render(float delta) {
		super.render(delta);
		hudRenderer.render(hud);
		playerRenderer.render(player);
        levelRender.render(level, delta);
        blocksRender.render(blockList);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (super.keyDown(keycode))
			return true;

		System.out.print(keycode);

		if (keycode == 51) { // w
			player.crosshairUp();
		}

		if (keycode == 47) { // s
			player.crosshairDown();
		}

		// FIXME: move into render loop
		if (keycode == 29) { // a
			player.moveLeft();
		}

		if (keycode == 32) { // d
			player.moveRight();
		}

		if (keycode == 62) // space
			player.jump();

		// if (keycode == 51) // w
		// hud.addMeters(3);
		//
		// if (hud.getMeters() > 100)
		// hud.clearMeters();
		//
		// hud.addScore(1);
		return true;
	};

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == 51 || keycode == 47) { // w or s
			player.crosshairMiddle();
		}

		return true;
	};

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
