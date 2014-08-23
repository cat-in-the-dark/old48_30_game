package com.catinthedark.game.assets;

import java.awt.Font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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
		public TextureRegion heartReg;
		public TextureRegion hudWireReg;
        public TextureRegion playerReg;
        public TextureRegion blockReg;

		@Override
		public void init(Config conf) {
			logoTex = new Texture(
					Gdx.files.internal("texture/logo.png"));

			Texture heart = new
					Texture(Gdx.files.internal("texture/heart.png"));
			heart.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			heartReg = new TextureRegion(heart);

			Texture hudWire = new Texture(
					Gdx.files.internal("texture/hud_wire.png"));
			hudWire.setWrap(TextureWrap.Repeat, TextureWrap.ClampToEdge);
			hudWireReg = new TextureRegion(hudWire);

            Texture player = new Texture(Gdx.files.internal("texture/player.png"));
            playerReg = new TextureRegion(player);

            Texture block = new Texture(Gdx.files.internal("texture/block.png"));
            blockReg = new TextureRegion(block);
		}
	}

	public static class Fonts implements Initable {

		public BitmapFont hudFont;

		@Override
		public void init(Config conf) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
					Gdx.files.internal("font/impact.ttf"));
			FreeTypeFontParameter params = new FreeTypeFontParameter();
			params.size = 20;

			hudFont = generator.generateFont(params);
			hudFont.setColor(Color.WHITE);
			generator.dispose(); // don't forget to dispose to avoid memory
									// leaks!

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
