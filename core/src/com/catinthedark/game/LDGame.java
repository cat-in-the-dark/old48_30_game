package com.catinthedark.game;

import com.badlogic.gdx.Game;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.screen.ScreenChain;
import com.catinthedark.game.screen.TitleScreen;
import com.catinthedark.game.screen.impl.GameScreen;

public class LDGame extends Game {

	@Override
	public void create() {
		Config conf = new Config();
		// any settings here?

		// load game resources
		Assets.init(conf);
		

		ScreenChain chain = new ScreenChain(this);

		chain.add(new TitleScreen(conf, Assets.textures.logoTex, 1000));
		chain.add(new GameScreen(conf));
		chain.gotoFrame(0);

	}
}
