package com.catinthedark.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class ScreenChain {
	private final List<BasicScreen> screens = new ArrayList<BasicScreen>();
	private final Game game;
	private int currentFrame = 0;

	private static final RuntimeException exScreenMiss = new RuntimeException(
			"next frame does not exists! AZAZA!");

	public ScreenChain(Game game) {
		this.game = game;
	}

	public void add(BasicScreen screen) {
		screens.add(screen);
		screen.setChain(this);
	}

	public void next() {
		try {
			currentFrame++;
			set(screens.get(currentFrame));
		} catch (Exception ex) {
			throw new RuntimeException(
					"next frame does not exists! AZAZA!");
		}

	}

	public void prev() {

		try {
			currentFrame--;
			set(screens.get(currentFrame));
		} catch (Exception ex) {
			throw new RuntimeException(
					"next frame does not exists! AZAZA!");
		}
	}

	public void gotoFrame(int index) {
		currentFrame = index;
		try {
			set(screens.get(currentFrame));
		} catch (Exception ex) {
			throw new RuntimeException(
					"next frame does not exists! AZAZA!");
		}
	}

	private void set(BasicScreen screen) {
		Gdx.input.setInputProcessor(screen);
		game.setScreen(screen);
	}

	public int getCurrentFrame() {
		return currentFrame;
	}
}
