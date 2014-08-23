package com.catinthedark.game.screen.impl;

import java.util.ArrayList;
import java.util.List;

import render.BlocksRender;
import render.HudRenderer;
import render.LevelRender;
import render.PlayerRender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.hud.Hud;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.physics.PhysicsModel;
import com.catinthedark.game.screen.ResizableScreen;

import entity.Block;
import entity.Player;

public class GameScreen extends ResizableScreen {

	private final Camera camera;
	private final Hud hud;
	private final HudRenderer hudRenderer;
	private final PlayerRender playerRenderer;
    private final LevelRender levelRender;
    private final BlocksRender blocksRender;
    private Player player;
    private Level level;
    private List<Block> blockList;

	private final OrthographicCamera backgroundFarCamera = new OrthographicCamera(
			conf.VIEW_PORT_WIDTH, conf.VIEW_PORT_HEIGHT);
	private final int[] layers = new int[] { 0 };

	private SpriteBatch batch = new SpriteBatch();

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

		backgroundFarCamera.position.set(new float[] {
				conf.VIEW_PORT_WIDTH / 2,
				conf.VIEW_PORT_HEIGHT / 2, 0 });
		backgroundFarCamera.update();

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

		// draw far background image
		Assets.textures.backgroundFar.setView(backgroundFarCamera);
		Assets.textures.backgroundFar.render(layers);

		hudRenderer.render(hud);
		playerRenderer.render(player);
		
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			backgroundFarCamera.position.set(
					backgroundFarCamera.position.x + 0.15f,
					backgroundFarCamera.position.y,
					backgroundFarCamera.position.z);
			backgroundFarCamera.update();
		}

        levelRender.render(level, delta);
        blocksRender.render(blockList);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (super.keyDown(keycode))
			return true;

		System.out.print(keycode);

		if (keycode == Keys.W) { // w
			player.crosshairUp();
		}

		if (keycode == Keys.S) { // s
			player.crosshairDown();
		}

		// FIXME: move into render loop
		if (keycode == Keys.A) { // a
			player.moveLeft();
		}

		if (keycode == Keys.D) { // d
			player.moveRight();
		}

		if (keycode == Keys.SPACE) // space
			player.jump();

		if (keycode == Keys.ENTER)
			player.shot();

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
