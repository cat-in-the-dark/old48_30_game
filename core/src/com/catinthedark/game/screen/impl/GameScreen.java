package com.catinthedark.game.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.GameScore;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.level.AIManager;
import com.catinthedark.game.level.BotsGenerator;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.level.LevelGenerator;
import com.catinthedark.game.physics.HitTester;
import com.catinthedark.game.physics.PhysicsModel;
import com.catinthedark.game.screen.ResizableScreen;
import entity.*;
import render.*;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ResizableScreen {

	private OrthographicCamera camera;
	private HudRenderer hudRenderer;
	private PlayerRender playerRenderer;
	private LevelRender levelRender;
	private LevelGenerator levelGenerator;
	private CableRender cableRender;
	private HitTester hitTester;
	private BotsGenerator botsGenerator;
	private List<Renderable> animations = new ArrayList<Renderable>();

	private Player player;
	private Level level;
	private AIManager aiManager;
	private Cable cable;

	private OrthographicCamera backgroundFarCamera;
	private final int[] layers = new int[] { 0 };

	private Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;

	public GameScreen(Config conf) {
		super(conf);
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

	private void gameOver() {
		gotoFrame(4);
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
			GameScore.getInstance().decHealth();
			player.setDamaged(true);
		}
		if (GameScore.getInstance().getHealth() <= 0) {
			gameOver();
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
			for (final MushroomedCrab crab : damaged) {
				System.out.println("health:" + crab.healt);
				crab.healt -= 10;
				if (crab.healt < 0) {
					GameScore.getInstance().priceCrab();
					level.deleteEntity(crab);

					Assets.audios.crabDeath.play(1.5f);

					final Vector2 pos = new Vector2(crab.getBody()
							.getPosition().x, crab.getBody().getPosition().y);

					animations.add(new Renderable() {
						private float stateTime = 0;
						private final SpriteBatch batch = new SpriteBatch();

						@Override
						public boolean render(float delta, Camera camera) {

							batch.setProjectionMatrix(camera.combined);
							batch.begin();
							batch.draw(
									Assets.animations.mushroomedCrabMeltRight
											.getKeyFrame(stateTime),
									(pos.x - Constants.MUSHROOMED_CRAB_WIDTH / 2)
											* conf.UNIT_SIZE,
									(pos.y - Constants.MUSHROOMED_CRAB_HEIGHT / 2)
											* conf.UNIT_SIZE,
									Constants.MUSHROOMED_CRAB_WIDTH
											* conf.UNIT_SIZE,
									Constants.MUSHROOMED_CRAB_HEIGHT
											* conf.UNIT_SIZE);

							batch.end();

							stateTime += delta;

							if (stateTime > Constants.BOT_DEATH_ANIMATION_TIME) {
								batch.dispose();
								return false;
							}

							return true;
						}

					});

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
		// botsGenerator.step(delta, player);
		levelGenerator.updateLevel(level, camera);

		levelRender.render(level, delta);
		hudRenderer.render();
		playerRenderer.render(player);
		cableRender.render(cable);
		// render animations
		// lazy list init for perfomance optimizations
		List<Renderable> removedAnimations = null;
		for (Renderable animation : animations) {
			if (!animation.render(delta, camera)) {
				if (removedAnimations == null) {
					removedAnimations = new ArrayList<Renderable>();
				}

				removedAnimations.add(animation);
			}
		}

		if (removedAnimations != null)
			animations.removeAll(removedAnimations);

		float viewportLeftEdge = camera.position.x / conf.UNIT_SIZE
				- conf.VIEW_PORT_WIDTH / 2;
		if (player.getBody().getPosition().x < viewportLeftEdge + 1) {
			player.getBody().setLinearVelocity(0,
					player.getBody().getLinearVelocity().y);
			player.getBody()
					.getPosition()
					.set(viewportLeftEdge + 1, player.getBody().getPosition().y);
		}

		// FIXME: move into render loop
		if (Gdx.input.isKeyPressed(Keys.A)) { // a
			if (player.getBody().getLinearVelocity().x > -10
					&& player.getBody().getPosition().x > viewportLeftEdge + 1) {
				player.moveLeft();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.D)) { // d
			if (player.getBody().getLinearVelocity().x < 10) {
				player.moveRight();
			}
		}

		debugRenderer.render(level.getWorld(), debugMatrix);
		aiManager.update(level, player);

		if (needMoveCamera()) {
			moveMainCamera();
			moveBackgroundCamera();
		}

		if (player.getBody().getPosition().y < 0) {
			gameOver();
		}
	}

	private void moveMainCamera() {

		GameScore.getInstance().incDistance();

		if (GameScore.getInstance().isWalked()) {
			next();
		}

		camera.position.set(camera.position.x + Constants.MAIN_CAMERA_SPEED,
				camera.position.y, camera.position.z);
		camera.update();

	}

	private void moveBackgroundCamera() {
		if (backgroundFarCamera.position.x >= conf.VIEW_PORT_WIDTH / 2 + 2
				* conf.VIEW_PORT_WIDTH) {
			backgroundFarCamera.position.set(new float[] {
					conf.VIEW_PORT_WIDTH / 2,
					conf.VIEW_PORT_HEIGHT / 2, 0 });
		} else {
			backgroundFarCamera.position.set(
					backgroundFarCamera.position.x
							+ Constants.BACK_CAMERA_SPEED,
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
		GameScore.getInstance().resetScore();

		camera = new OrthographicCamera(conf.VIEW_PORT_WIDTH
				* conf.UNIT_SIZE,
				conf.VIEW_PORT_HEIGHT * conf.UNIT_SIZE);
		this.camera.position.x = conf.VIEW_PORT_WIDTH * conf.UNIT_SIZE / 2;
		this.camera.position.y = conf.VIEW_PORT_HEIGHT * conf.UNIT_SIZE / 2;
		this.camera.update();

		this.backgroundFarCamera = new OrthographicCamera(
				conf.VIEW_PORT_WIDTH, conf.VIEW_PORT_HEIGHT);

		hudRenderer = new HudRenderer(conf);

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
