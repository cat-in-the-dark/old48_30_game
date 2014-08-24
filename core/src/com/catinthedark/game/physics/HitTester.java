package com.catinthedark.game.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.catinthedark.game.level.Level;

import entity.Block;
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
				for (Block block : level.getBlockList()) {
					bodyList.add(block.getBody());
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
}
