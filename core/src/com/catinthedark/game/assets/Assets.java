package com.catinthedark.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;

import java.util.ArrayList;
import java.util.List;

public class Assets {
	private interface Initable {
		public void init(Config conf);
	}

	public static class Audios implements Initable {
		public Sound shot;
		public Sound jump;
		public Sound landing;

		@Override
		public void init(Config conf) {
			shot = Gdx.audio
					.newSound(Gdx.files.internal("sound/shot_sfx.mp3"));
			jump = Gdx.audio
					.newSound(Gdx.files.internal("sound/jump_sfx.wav"));
			landing = Gdx.audio
					.newSound(Gdx.files.internal("sound/landing_sfx.wav"));

		}

	}

	public static class Textures implements Initable {
		public Texture logoTex;
		public TextureRegion heartReg;
		public TextureRegion hudWireReg;
		public TextureRegion playerReg;
		public TextureRegion[][] groundReg;

        public TextureRegion underground;
        public TextureRegion grass;
        public TextureRegion grass_shadow;
        public TextureRegion grass_slope_slope;
        public TextureRegion grass_slope_left_shadow;
        public TextureRegion grass_slope_right;
        public TextureRegion grass_slope_right_shadow;
        public TextureRegion empty;

		public Texture shot;
		public TextureRegion[][] playerFrames;
		public TextureRegion[][] playerFramesBack;

		public Texture mushroomedCrabSheet;
		public TextureRegion[][] mushroomedCrabFramesLeft;
		public TextureRegion[][] mushroomedCrabFramesRight;

		public TiledMapRenderer backgroundFar;
		public TiledMapRenderer background;

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

			TiledMap backgroundFarMap = new TmxMapLoader()
					.load("background_far.tmx");
			backgroundFar = new OrthogonalTiledMapRenderer(backgroundFarMap,
					1 / 32f);

			Texture ground = new Texture(Gdx.files.internal("texture/ground.png"));
			groundReg = TextureRegion.split(ground, 32, 32);  // 10 x 4
            underground = groundReg[3][0];
            grass = groundReg[1][0];
            grass_shadow = groundReg[2][0];
            grass_slope_slope = groundReg[0][1];
            grass_slope_left_shadow = groundReg[1][1];
            grass_slope_right = groundReg[0][6];
            grass_slope_right_shadow = groundReg[1][6];
            empty = groundReg[0][0];

			shot = new Texture(Gdx.files.internal("texture/shot.png"));

			playerFrames = TextureRegion.split(
					new Texture(Gdx.files.internal("texture/man.png")),
					(int) Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
					(int) Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);

			playerFramesBack = TextureRegion.split(
					new Texture(Gdx.files.internal("texture/man.png")),
					(int) Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
					(int) Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
			for (TextureRegion reg : playerFramesBack[0]) {
				reg.flip(true, false);
			}

			mushroomedCrabSheet = new Texture(
					Gdx.files.internal("texture/mushroomed_crab.png"));
			mushroomedCrabFramesLeft = TextureRegion.split(
					mushroomedCrabSheet,
					(int) Constants.MUSHROOMED_CRAB_WIDTH * conf.UNIT_SIZE,
					(int) Constants.MUSHROOMED_CRAB_HEIGHT * conf.UNIT_SIZE);
			mushroomedCrabFramesRight = TextureRegion.split(
					mushroomedCrabSheet,
					(int) Constants.MUSHROOMED_CRAB_WIDTH * conf.UNIT_SIZE,
					(int) Constants.MUSHROOMED_CRAB_HEIGHT * conf.UNIT_SIZE);
			for (TextureRegion reg : mushroomedCrabFramesRight[0]) {
				reg.flip(true, false);
			}
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
		public Animation playerJump;
		public Animation playerJumpBack;
		public Animation playerGo;
		public Animation playerGoBack;

        public Animation mushroomedCrabIdleLeft;
        public Animation mushroomedCrabIdleRight;
        public Animation mushroomedCrabRunRight;
        public Animation mushroomedCrabRunLeft;
        public Animation mushroomedCrabFireLeft;
        public Animation mushroomedCrabFireRight;
        public Animation mushroomedCrabMeltRight;
        public Animation mushroomedCrabMeltLeft;

        private TextureRegion[] selectRegions(TextureRegion[][] frames, int[] framesNumbers) {
            List<TextureRegion> regions = new ArrayList<TextureRegion>(framesNumbers.length);
            for (int index : framesNumbers) {
                regions.add(frames[0][index]);
            }

            return regions.toArray(new TextureRegion[regions.size()]);
        }

		@Override
		public void init(Config conf) {

			playerJump = new Animation(0.05f,
                    selectRegions(textures.playerFrames, new int[]{14,15}));
			playerJump.setPlayMode(Animation.PlayMode.NORMAL);

			playerJumpBack = new Animation(0.05f,
                    selectRegions(textures.playerFramesBack, new int[]{14, 15}));
			playerJumpBack.setPlayMode(Animation.PlayMode.NORMAL);

			playerGo = new Animation(0.05f,
                    selectRegions(textures.playerFrames, new int[]{0,1,2,3,4,5,6,7,8,9}));
			playerGo.setPlayMode(Animation.PlayMode.LOOP);

			playerGoBack = new Animation(0.05f, selectRegions(textures.playerFramesBack,
                    new int[]{0,1,2,3,4,5,6,7,8,9}));
			playerGoBack.setPlayMode(Animation.PlayMode.LOOP);

            mushroomedCrabIdleLeft = new Animation(Constants.ANIMATION_SPEED,
                    new TextureRegion[]{
                            textures.mushroomedCrabFramesLeft[0][2]
                    });
            mushroomedCrabIdleLeft.setPlayMode(Animation.PlayMode.NORMAL);

            mushroomedCrabIdleRight = new Animation(Constants.ANIMATION_SPEED,
                    new TextureRegion[]{
                            textures.mushroomedCrabFramesRight[0][2]
                    });
            mushroomedCrabIdleRight.setPlayMode(Animation.PlayMode.NORMAL);

            mushroomedCrabRunLeft = new Animation(Constants.ANIMATION_SPEED,
                    selectRegions(textures.mushroomedCrabFramesLeft, new int[]{0,1,2,3,4}));
            mushroomedCrabRunLeft.setPlayMode(Animation.PlayMode.LOOP);

            mushroomedCrabRunRight = new Animation(Constants.ANIMATION_SPEED,
                    selectRegions(textures.mushroomedCrabFramesRight, new int[]{0,1,2,3,4}));
            mushroomedCrabRunRight.setPlayMode(Animation.PlayMode.LOOP);

            mushroomedCrabFireRight = new Animation(Constants.ANIMATION_SPEED,
                    selectRegions(textures.mushroomedCrabFramesRight, new int[]{5,6,7}));
            mushroomedCrabFireRight.setPlayMode(Animation.PlayMode.LOOP);

            mushroomedCrabFireLeft = new Animation(Constants.ANIMATION_SPEED,
                    selectRegions(textures.mushroomedCrabFramesLeft, new int[]{5,6,7}));
            mushroomedCrabFireLeft.setPlayMode(Animation.PlayMode.LOOP);

            mushroomedCrabMeltRight = new Animation(Constants.ANIMATION_SPEED,
                    selectRegions(textures.mushroomedCrabFramesRight, new int[]{8,9,10,11,12,13}));
            mushroomedCrabMeltRight.setPlayMode(Animation.PlayMode.LOOP);

            mushroomedCrabMeltLeft = new Animation(Constants.ANIMATION_SPEED,
                    selectRegions(textures.mushroomedCrabFramesLeft, new int[]{8,9,10,11,12,13}));
            mushroomedCrabMeltLeft.setPlayMode(Animation.PlayMode.LOOP);
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
