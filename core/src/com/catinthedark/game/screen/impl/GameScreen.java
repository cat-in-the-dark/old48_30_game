package com.catinthedark.game.screen.impl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.catinthedark.game.Config;
import com.catinthedark.game.screen.ResizableScreen;

public class GameScreen extends ResizableScreen {

	private final Camera camera;

	public GameScreen(Config conf) {
		super(conf);

		this.camera = new OrthographicCamera(conf.VIEW_PORT_WIDTH,
				conf.VIEW_PORT_HEIGHT);
		this.camera.update();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

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
