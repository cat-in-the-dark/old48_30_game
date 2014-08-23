package com.catinthedark.game.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.hud.Hud;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.physics.PhysicsModel;
import com.catinthedark.game.screen.ResizableScreen;
import entity.Block;
import entity.Player;
import render.BlocksRender;
import render.HudRenderer;
import render.LevelRender;
import render.PlayerRender;

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

        level = new Level(this, Constants.EASY);
        levelRender = new LevelRender();

        player = createPlayer(level.getWorld());
        player.getModel().getFixture().setFriction(Constants.FRICTION);

		player.moveRight();
		player.crosshairMiddle();

		backgroundFarCamera.position.set(new float[] {
				conf.VIEW_PORT_WIDTH / 2,
				conf.VIEW_PORT_HEIGHT / 2, 0 });
		backgroundFarCamera.update();

        blockList = new ArrayList<Block>();
        for (int i = 0; i < conf.VIEW_PORT_WIDTH; i++) {
            blockList.add(createBlock(level.getWorld(), i));
        }
	}


    public Camera getCamera() {
        return camera;
    }

    private Player createPlayer(World world) {
        CircleShape playerShape = new CircleShape();
        playerShape.setRadius(Constants.PLAYER_WIDTH / 2);
        PhysicsModel playerModel = new PhysicsModel(world, 0, 5, playerShape, true, BodyDef.BodyType.DynamicBody, 0.1f);
        return new Player(playerModel);
    }

    private Block createBlock(World world, int x) {
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT / 2);
        PhysicsModel blockModel = new PhysicsModel(world, x, 0, blockShape, true, BodyDef.BodyType.StaticBody, 1.0f);
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

		if (keycode == Keys.SPACE) {  // space
            Array<Contact> contactList = level.getWorld().getContactList();
            for (int i = 0; i < contactList.size; i++) {
                Contact contact = contactList.get(i);
                if (contact.isTouching()) {

                    List<Body> bodyList = new ArrayList<Body>();
                    for (Block block : blockList) {
                        bodyList.add(block.getBody());
                    }

                    if (contact.getFixtureA() == player.getModel().getFixture()) {
                        if (bodyList.contains(contact.getFixtureB().getBody())) {
                            player.jump();
                            break;
                        }
                    } else if (contact.getFixtureB() == player.getModel().getFixture()) {
                        if (bodyList.contains(contact.getFixtureA().getBody())) {
                            player.jump();
                            break;
                        }
                    }
                }
            }
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
