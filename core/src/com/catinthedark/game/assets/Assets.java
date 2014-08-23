package com.catinthedark.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.catinthedark.game.Config;

public class Assets {
	private interface Initable {
		public void init(Config conf);
	}

	public static class Audios implements Initable {

		@Override
		public void init(Config conf) {
			// TODO Auto-generated method stub

		}

	}

	public static class Textures implements Initable {
		public Texture logoTex;

		@Override
		public void init(Config conf) {
			logoTex = new Texture(
					Gdx.files.internal("texture/logo.png"));
		}

	}

	public static class Fonts implements Initable {

		@Override
		public void init(Config conf) {
			// TODO Auto-generated method stub

		}

	}

	public static class Animations implements Initable {

		@Override
		public void init(Config conf) {
			// TODO Auto-generated method stub

		}

	}

	public static Audios audios = new Audios();
	public static Textures textures = new Textures();
	public static Fonts fonts = new Fonts();
	public static Animations animations = new Animations();

	public static void init(Config conf) {
		audios.init(conf);
		textures.init(conf);
		fonts.init(conf);
		animations.init(conf);
	}
}
