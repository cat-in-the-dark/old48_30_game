package com.catinthedark.game.hud;

public class Hud {
	private int health;
	private int score;
	private int meters;
	private int level;

	public Hud(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void incHealth() {
		health++;
	}

	public void decHealth() {
		health--;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int intLevel() {
		return ++level;
	}

	public int getScore() {
		return score;
	}

	public void addMeters(int meters) {
		this.meters += meters;
	}

	public void clearMeters() {
		meters = 0;
	}

	public int getMeters() {
		return meters;
	}
}
