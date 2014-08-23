package com.catinthedark.game.screen;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.catinthedark.game.Config;

/**
 * Created by Ilya on 26.04.2014.
 */
public abstract class BasicScreen extends InputAdapter implements Screen {
	protected ScreenChain chain;
	protected final Config conf;

	public BasicScreen(Config conf) {
		this.conf = conf;
	}

	public void setChain(ScreenChain chain) {
		this.chain = chain;
	}

	protected int getCurrentFrame() {
		return chain.getCurrentFrame();
	}

	protected void next() {
		chain.next();
	}

	protected void prev() {
		chain.prev();
	}

	protected void gotoFrame(int index) {
		chain.gotoFrame(index);
	}
}
