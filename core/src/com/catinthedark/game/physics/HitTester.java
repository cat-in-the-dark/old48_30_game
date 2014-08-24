package com.catinthedark.game.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.Array;
import com.catinthedark.game.level.Level;

import com.catinthedark.game.level.Tile;
import entity.Block;
import entity.DirectionX;
import entity.DirectionY;
import entity.MushroomedCrab;
import entity.Player;

public class HitTester {

	private final Level level;

	public HitTester(Level level) {
		this.level = level;
	}

	public boolean isPlayerFlyes(Player player) {
		Array<Contact> contactList = level.getWorld().getContactList();
		for (int i = 0; i < contactList.size; i++) {
			Contact contact = contactList.get(i);
			if (contact.isTouching()) {

				List<Body> bodyList = new ArrayList<Body>();
				for (Tile tile : level.tiles) {
					bodyList.add(tile.body);
				}

				if (contact.getFixtureA() == player.getModel().getFixture()) {
					if (bodyList.contains(contact.getFixtureB().getBody()))
						return false;
				} else if (contact.getFixtureB() == player.getModel()
						.getFixture()) {
					if (bodyList.contains(contact.getFixtureA().getBody()))
						return false;
				}
			}
		}

		return true;
	}

	public List<MushroomedCrab> getCrubsOnSuffering(Player player) {
		final List<MushroomedCrab> crabs = level.getCrabs();
		final List<MushroomedCrab> suffered = new ArrayList<MushroomedCrab>();
		Vector2 ppos = player.getBody().getPosition();
		Vector2 fireTo = null;
		if (player.getDirX() == DirectionX.RIGHT) {
			switch (player.getDirY()) {
			case CROSSHAIR_UP:
				fireTo = new Vector2(ppos.x + 1, ppos.y + 1);
				break;
			case CROSSHAIR_DOWN:
				fireTo = new Vector2(ppos.x + 1, ppos.y - 1);
				break;
			case CROSSHAIR_MIDDLE:
				fireTo = new Vector2(ppos.x + 8, ppos.y);
				break;
			}
		} else {
			switch (player.getDirY()) {
			case CROSSHAIR_UP:
				fireTo = new Vector2(ppos.x - 1, ppos.y + 1);
				break;
			case CROSSHAIR_DOWN:
				fireTo = new Vector2(ppos.x - 1, ppos.y - 1);
				break;
			case CROSSHAIR_MIDDLE:
				fireTo = new Vector2(ppos.x - 1, ppos.y);
				break;
			}

		}

		// find all concats with player shot
		level.getWorld().rayCast(new RayCastCallback() {

			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point,
					Vector2 normal, float fraction) {
				for (MushroomedCrab crab : crabs) {
					if (crab.getBody() == fixture.getBody()) {
						suffered.add(crab);
						System.out.print("add");
					}
				}

				return -1;
			}
		}, player.getBody().getPosition(), fireTo);

		return suffered;

	}
}
