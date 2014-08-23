package com.catinthedark.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.game.Config;

public abstract class ResizableScreen extends SwitchableScreen {

	private Rectangle viewport;

	public ResizableScreen(Config conf) {
		super(conf);
		viewport = new Rectangle(0, 0, conf.VIEW_PORT_WIDTH * conf.UNIT_SIZE,
				conf.VIEW_PORT_HEIGHT * conf.UNIT_SIZE);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		if (width == conf.UNIT_SIZE * conf.VIEW_PORT_WIDTH &&
				height == conf.UNIT_SIZE * conf.VIEW_PORT_HEIGHT)
			return;
		System.out.print(String.format("resize:(%s, %s)", width, height));
		// calculate new viewport
		float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if (aspectRatio > conf.ASPECT_RATIO) {
			scale = (float) height / (float) conf.VIEW_PORT_HEIGHT;
			crop.x = (width - conf.VIEW_PORT_WIDTH * scale) / 2f;
		}
		else if (aspectRatio < conf.ASPECT_RATIO) {
			scale = (float) width / (float) conf.VIEW_PORT_WIDTH;
			crop.y = (height - conf.VIEW_PORT_HEIGHT * scale) / 2f;
		}
		else {
			scale = (float) width / (float) conf.ASPECT_RATIO;
		}

		float w = (float) conf.VIEW_PORT_WIDTH * scale;
		float h = (float) conf.VIEW_PORT_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);

		System.out.print(String.format("view:(%s)", viewport));

		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);
	}
}
