package com.catinthedark.game.screen.impl;

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
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.hud.Hud;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.level.LevelGenerator;
import com.catinthedark.game.physics.HitTester;
import com.catinthedark.game.physics.PhysicsModel;
import com.catinthedark.game.screen.ResizableScreen;

import entity.Player;

public class GameScreen extends ResizableScreen {

	private final Camera camera;
	private final Hud hud;
	private final HudRenderer hudRenderer;
	private final PlayerRender playerRenderer;
	private final LevelRender levelRender;
	private final LevelGenerator levelGenerator = new LevelGenerator();
	private final BlocksRender blocksRender;
	private final HitTester hitTester;
	private Player player;
	private Level level;

	private final OrthographicCamera backgroundFarCamera = new OrthographicCamera(
			conf.VIEW_PORT_WIDTH, conf.VIEW_PORT_HEIGHT);
	private final int[] layers = new int[] { 0 };

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

		level = new Level(conf, Constants.EASY);
		levelRender = new LevelRender();
		levelGenerator.generateLevel(level);

		hitTester = new HitTester(level);

		player = createPlayer(level.getWorld());
		player.getModel().getFixture().setFriction(Constants.FRICTION);

		player.moveRight();
		player.crosshairMiddle();

		backgroundFarCamera.position.set(new float[] {
				conf.VIEW_PORT_WIDTH / 2,
				conf.VIEW_PORT_HEIGHT / 2, 0 });
		backgroundFarCamera.update();
	}

	public Camera getCamera() {
		return camera;
	}

	private Player createPlayer(World world) {
		CircleShape playerShape = new CircleShape();
		playerShape.setRadius(Constants.PLAYER_WIDTH / 2);
		PhysicsModel playerModel = new PhysicsModel(world, 0, 5, playerShape,
				true, BodyDef.BodyType.DynamicBody, 0.1f);
		return new Player(playerModel);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		// draw far background image
		Assets.textures.backgroundFar.setView(backgroundFarCamera);
		Assets.textures.backgroundFar.render(layers);

		player.update(delta);

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
		// only for dev
		blocksRender.render(level.getBlockList());
		
		//check here for camera move
		//call levelGenerator

		// FIXME: move into render loop
		if (Gdx.input.isKeyPressed(Keys.A)) { // a
			if (player.getBody().getLinearVelocity().x > -10) {
				player.moveLeft();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.D)) { // d
			if (player.getBody().getLinearVelocity().x < 10) {
				player.moveRight();
			}
		}
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

		if (keycode == Keys.SPACE) { // space
			if (!hitTester.isPlayerFlyes(player))
				player.jump();
		}

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
