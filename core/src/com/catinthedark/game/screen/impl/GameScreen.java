package com.catinthedark.game.screen.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import render.BlocksRender;
import render.HudRenderer;
import render.LevelRender;
import render.PlayerRender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.hud.Hud;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.level.LevelGenerator;
import com.catinthedark.game.physics.HitTester;
import com.catinthedark.game.physics.PhysicsModel;
import com.catinthedark.game.screen.ResizableScreen;
import entity.Block;
import entity.Cable;
import entity.Player;
import render.*;

import entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ResizableScreen {

	private final Camera camera;
	private final Hud hud;
	private final HudRenderer hudRenderer;
	private final PlayerRender playerRenderer;
	private final LevelRender levelRender;
	private final LevelGenerator levelGenerator = new LevelGenerator();
    private final BlocksRender blocksRender;
    private final CableRender cableRender;
    private final HitTester hitTester;

    private Player player;
    private Level level;
    private List<Block> blockList;
    private Cable cable;

	private final OrthographicCamera backgroundFarCamera = new OrthographicCamera(
			conf.VIEW_PORT_WIDTH, conf.VIEW_PORT_HEIGHT);
	private final int[] layers = new int[] { 0 };

    private ShapeRenderer shapeRenderer;

    private Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

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

        cableRender = new CableRender(conf);

        debugRenderer = new Box2DDebugRenderer();
        debugMatrix=new Matrix4(camera.combined);

		backgroundFarCamera.position.set(new float[] {
				conf.VIEW_PORT_WIDTH / 2,
				conf.VIEW_PORT_HEIGHT / 2, 0 });
		backgroundFarCamera.update();

        blockList = new ArrayList<Block>();
        for (int i = 0; i < conf.VIEW_PORT_WIDTH / 2; i++) {
            blockList.add(createBlock(level.getWorld(), i));
        }

        cable = new Cable(level.getWorld(), new Vector2(5, 5), 1.0f, 40);

        player = createPlayer(level.getWorld(), cable);
        player.getModel().getFixture().setFriction(Constants.FRICTION);

        player.moveRight();
        player.crosshairMiddle();
	}

	public Camera getCamera() {
		return camera;
	}

    private Player createPlayer(World world, Cable cable) {
        CircleShape playerShape = new CircleShape();
        playerShape.setRadius(Constants.PLAYER_WIDTH / 2);
        PhysicsModel playerModel = new PhysicsModel(world, 0, 5, playerShape, true, BodyDef.BodyType.DynamicBody, 0.1f);
        Player player = new Player(playerModel);

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = player.getBody();
        jointDef.bodyB = cable.getBodyList().get(0);
        world.createJoint(jointDef);

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.bodyA = player.getBody();
        weldJointDef.bodyB = cable.getBodyList().get(cable.getBodyList().size() - 1);
        Vector2 playerPos = player.getBody().getPosition();
        weldJointDef.localAnchorA.set(-35, 3);
        world.createJoint(weldJointDef);

        return player;
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

		boolean isStayByPhysics = !hitTester.isPlayerFlyes(player);

		if (Gdx.input.isKeyPressed(Keys.SPACE) && isStayByPhysics) {
			player.update(delta, false);
			player.jump();
		}

		if (isStayByPhysics == true)
			player.update(delta, true);
		else
			player.update(delta);

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D))
			player.setMooving(true);
		else
			player.setMooving(false);

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

		// check here for camera move
		// call levelGenerator

        cableRender.render(cable);

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
        debugRenderer.render(level.getWorld(), debugMatrix);
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

		// if (keycode == Keys.SPACE) { // space
		// if (player.isStay())
		// player.jump();
		// }

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
