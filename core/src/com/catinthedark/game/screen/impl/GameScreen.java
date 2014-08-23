package com.catinthedark.game.screen.impl;

import render.HudRenderer;
import render.PlayerRender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.catinthedark.game.Config;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.hud.Hud;
import com.catinthedark.game.screen.ResizableScreen;

import entity.Player;

public class GameScreen extends ResizableScreen {

	private final Camera camera;
	private final Hud hud;
	private final HudRenderer hudRenderer;
	private final Player player = new Player();
	private final PlayerRender playerRenderer;

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

		player.moveRight();
		player.crosshairMiddle();

		backgroundFarCamera.position.set(new float[] {
				conf.VIEW_PORT_WIDTH / 2,
				conf.VIEW_PORT_HEIGHT / 2, 0 });
		backgroundFarCamera.update();
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
