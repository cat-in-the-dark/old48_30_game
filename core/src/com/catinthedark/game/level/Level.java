package com.catinthedark.game.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;
import entity.Block;
import entity.Bullet;
import entity.Entity;
import entity.MushroomedCrab;

import java.util.*;

/**
 * Created by Ilya on 23.08.2014.
 */
public class Level {
	public List<Tile> tiles;
	public final int difficult;
	private World world;
	private List<Block> blockList;
	private Map<Class, List<Entity>> entities = new HashMap<Class, List<Entity>>();
    private List<Bullet> bullets;
	private Class[] entities_classes = { MushroomedCrab.class };

	public Level(Config conf, int difficult, OrthographicCamera camera) {
		world = new World(new Vector2(0, Constants.WORLD_GRAVITY), true);
		blockList = new ArrayList<Block>();
        bullets = new ArrayList<Bullet>();

		this.difficult = difficult;
		this.tiles = new LinkedList<Tile>();

		for (Class klass : entities_classes) {
			entities.put(klass, new ArrayList<Entity>());
		}
	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public World getWorld() {
		return world;
	}

	public void addEntity(Entity entity) {
		List<Entity> list = entities.get(entity.getClass());
		if (list == null) {
			list = new ArrayList<Entity>();
			entities.put(entity.getClass(), list);
		}

		list.add(entity);
	}

	public void deleteEntity(Entity entity) {
		entities.get(entity.getClass()).remove(entity);
	}

	@SuppressWarnings("unchecked")
	public List<MushroomedCrab> getCrabs() {
		List<Entity> crabs = entities.get(MushroomedCrab.class);
		return (List<MushroomedCrab>) (crabs == null ? Collections.emptyList()
				: crabs);
	}

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void update(float delta) {
        for (Entity entity : getCrabs()) {
            entity.update(delta);
        }

        for (Bullet bullet : getBullets()) {
            bullet.update(delta);
        }
    }
}
