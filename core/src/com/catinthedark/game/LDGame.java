package com.catinthedark.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.screen.GameEndScreen;
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

        TitleScreen startGame = new TitleScreen(conf, Assets.textures.startGameTex, 0);
        startGame.bindings.bindNext(Input.Keys.ENTER);

        TitleScreen gameOver = new GameEndScreen(conf, Assets.textures.gameOverTex, 0);
        gameOver.bindings.bindNext(Input.Keys.ESCAPE);

        TitleScreen gameWin = new GameEndScreen(conf, Assets.textures.winTex, 0);
        gameWin.bindings.bindNext(Input.Keys.ESCAPE);

		chain.add(new TitleScreen(conf, Assets.textures.logoTex, 1000));
        chain.add(startGame);
        chain.add(new GameScreen(conf));
        chain.add(gameWin);
        chain.add(gameOver);
		chain.gotoFrame(0);

	}
}
