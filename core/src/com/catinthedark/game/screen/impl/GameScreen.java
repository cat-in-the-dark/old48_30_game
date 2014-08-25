package com.catinthedark.game.screen.impl;

import java.util.ArrayList;
import java.util.List;

import render.BlocksRender;
import render.CableRender;
import render.HudRenderer;
import render.LevelRender;
import render.PlayerRender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.hud.Hud;
import com.catinthedark.game.level.AIManager;
import com.catinthedark.game.level.BotsGenerator;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.level.LevelGenerator;
import com.catinthedark.game.physics.HitTester;
import com.catinthedark.game.physics.PhysicsModel;
import com.catinthedark.game.screen.ResizableScreen;

import entity.Block;
import entity.Bullet;
import entity.Cable;
import entity.Mushroom;
import entity.MushroomedCrab;
import entity.Player;

public class GameScreen extends ResizableScreen {

	private final OrthographicCamera camera;
	private final Hud hud;
	private final HudRenderer hudRenderer;
	private final PlayerRender playerRenderer;
	private final LevelRender levelRender;
	private final LevelGenerator levelGenerator;
	private final CableRender cableRender;
	private final HitTester hitTester;
	private final BotsGenerator botsGenerator;
	private final List<Renderable> animations = new ArrayList<Renderable>();

	private Player player;
	private Level level;
	private AIManager aiManager;
	private Cable cable;

	private final OrthographicCamera backgroundFarCamera = new OrthographicCamera(
			conf.VIEW_PORT_WIDTH, conf.VIEW_PORT_HEIGHT);
	private final int[] layers = new int[] { 0 };

	private Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;

	public float walkedDistance = 0;

	public GameScreen(Config conf) {
		super(conf);

		this.camera = new OrthographicCamera(conf.VIEW_PORT_WIDTH
				* conf.UNIT_SIZE,
				conf.VIEW_PORT_HEIGHT * conf.UNIT_SIZE);
		this.camera.position.x = conf.VIEW_PORT_WIDTH * conf.UNIT_SIZE / 2;
		this.camera.position.y = conf.VIEW_PORT_HEIGHT * conf.UNIT_SIZE / 2;
		this.camera.update();

		hud = new Hud(10);
		hudRenderer = new HudRenderer(conf);
		hud.setLevel(1);

		playerRenderer = new PlayerRender(conf, camera);

		level = new Level(conf, Constants.EASY, camera);
		this.aiManager = new AIManager();
		levelRender = new LevelRender(conf, camera);

		levelGenerator = new LevelGenerator(conf, level);
		levelGenerator.generateLevel(level);

		hitTester = new HitTester(level);

		cableRender = new CableRender(conf, camera);

		botsGenerator = new BotsGenerator(level);

		debugRenderer = new Box2DDebugRenderer();
		debugMatrix = new Matrix4(camera.combined);

		backgroundFarCamera.position.set(new float[] {
				conf.VIEW_PORT_WIDTH / 2,
				conf.VIEW_PORT_HEIGHT / 2, 0 });
		backgroundFarCamera.update();

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
		PhysicsModel playerModel = new PhysicsModel(world, 0, 5, playerShape,
				true, BodyDef.BodyType.DynamicBody, 0.1f);
		Player player = new Player(playerModel);

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = player.getBody();
		jointDef.bodyB = cable.getBodyList().get(0);
		world.createJoint(jointDef);

		WeldJointDef weldJointDef = new WeldJointDef();
		weldJointDef.bodyA = player.getBody();
		weldJointDef.bodyB = cable.getBodyList().get(
				cable.getBodyList().size() - 1);
		weldJointDef.localAnchorA.set(-30, 8);
		world.createJoint(weldJointDef);

		return player;
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		// draw far background image
		Assets.textures.backgroundFar.setView(backgroundFarCamera);
		Assets.textures.backgroundFar.render(layers);

		player.update(delta);
		level.update(delta);

		if (hitTester.isPlayerOnDamage(player)) {
			System.out.print("damage");
			hud.decHealth();
		}

		boolean isStayByPhysics = !hitTester.isPlayerFlyes(player);

		if (Gdx.input.isKeyPressed(Keys.SPACE) && isStayByPhysics) {
			player.jump();
			player.setOnGround(false);
		}

		if (isStayByPhysics == true)
			player.setOnGround(true);

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D))
			player.setMooving(true);
		else
			player.setMooving(false);

		if (player.isInAttack()) {
			List<MushroomedCrab> damaged =
					hitTester.getCrubsOnSuffering(player);
			for (MushroomedCrab crab : damaged) {
				System.out.println("healt:" + crab.healt);
				crab.healt -= 10;
				if (crab.healt < 0) {
					level.deleteEntity(crab);
					level.getWorld().destroyBody(crab.getBody());
				}
			}

		}
		// update mushrooms
		List<Mushroom> forDelete = new ArrayList<Mushroom>();
		for (Bullet b : level.getBullets()) {
			Mushroom mush = (Mushroom) b;
			mush.update(delta);
			if (mush.getStateTime() > 2) {
				forDelete.add(mush);
				level.getWorld().destroyBody(mush.getBody());
			}
		}

		level.getBullets().removeAll(forDelete);

		// add bots
		botsGenerator.step(delta, player);
		levelGenerator.updateLevel(level, camera);

		levelRender.render(level, delta);
		hudRenderer.render(hud);
		playerRenderer.render(player);
		cableRender.render(cable);
		// render animations

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
		aiManager.update(level);

		if (needMoveCamera()) {
            moveMainCamera();
            moveBackgroundCamera();
        }

		if (player.getBody().getPosition().y < 0) {
			next();
		}
	}

	private void moveMainCamera() {

		walkedDistance += Constants.MAIN_CAMERA_SPEED;

		System.out.println(walkedDistance);
		hud.addMeters((int) (walkedDistance * 100)
				/ Constants.DISTANCE_MAX_EASY);
		System.out.println(walkedDistance);
		hud.setMeters((int) ((walkedDistance * 100) / Constants.DISTANCE_MAX_EASY));

		if (walkedDistance >= Constants.DISTANCE_MAX_EASY) {
			next();
		}

		camera.position.set(camera.position.x + Constants.MAIN_CAMERA_SPEED,
				camera.position.y, camera.position.z);
		camera.update();

	}

    private void moveBackgroundCamera() {
        if (backgroundFarCamera.position.x >= conf.VIEW_PORT_WIDTH / 2 + 2 * conf.VIEW_PORT_WIDTH) {
            backgroundFarCamera.position.set(new float[] {
                    conf.VIEW_PORT_WIDTH / 2,
                    conf.VIEW_PORT_HEIGHT / 2, 0 });
        } else {
            backgroundFarCamera.position.set(
                    backgroundFarCamera.position.x + Constants.BACK_CAMERA_SPEED,
                    backgroundFarCamera.position.y,
                    backgroundFarCamera.position.z);
        }
        backgroundFarCamera.update();
    }

	private boolean needMoveCamera() {
		float distance = player.getBody().getPosition().x * conf.UNIT_SIZE
				- camera.position.x;
		return distance > Constants.MAX_DISTANCE_CAMERA_AHEAD
				* conf.UNIT_SIZE;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (super.keyDown(keycode))
			return true;

		System.out.print("ppp" + keycode);

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
