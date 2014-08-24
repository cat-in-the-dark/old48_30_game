package com.catinthedark.game.screen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.game.Config;

public class TitleScreen extends ResizableScreen {
	private final Texture texture;
	private final int timeToLive;
	private final boolean infinite;

	protected final SpriteBatch spriteBatch = new SpriteBatch();
	private float timeLaps;

	private final Camera camera;

	public TitleScreen(Config conf, Texture texture, int timeToLive) {
		super(conf);

		this.texture = texture;
		this.timeToLive = timeToLive;
		infinite = timeToLive == 0 ? true : false;

		this.camera = new OrthographicCamera(conf.VIEW_PORT_WIDTH,
				conf.VIEW_PORT_HEIGHT);
		this.camera.update();
	}

	@Override
	public void render(float delta) {
		//System.out.print("render");
		super.render(delta);

		spriteBatch.begin();
		spriteBatch.enableBlending();
		spriteBatch.draw(texture, 0, 0, conf.VIEW_PORT_WIDTH
				* conf.UNIT_SIZE, conf.VIEW_PORT_HEIGHT
				* conf.UNIT_SIZE);
		spriteBatch.end();

		if (!infinite)
			timeLaps += delta;

		if (timeLaps * 1000 > timeToLive)
			next();
	}

	@Override
	public void show() {
		timeLaps = 0.0f;
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
		spriteBatch.dispose();
	}
}
