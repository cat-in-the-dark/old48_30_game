package com.catinthedark.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
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

        TitleScreen startGame = new TitleScreen(conf, Assets.textures.startGameTex, 0);
        startGame.bindings.bindNext(Input.Keys.ENTER);

        TitleScreen gameOver = new TitleScreen(conf, Assets.textures.gameOverTex, 0){
            @Override
            public void render(float delta){
                super.render(delta);
                spriteBatch.begin();
                Assets.fonts.hudFont.draw(spriteBatch, "Score: " + GameScore.getInstance().getScore(), conf.VIEW_PORT_WIDTH, conf.VIEW_PORT_HEIGHT);
                spriteBatch.end();
            }
        };
        gameOver.bindings.bindNext(Input.Keys.ESCAPE);

        TitleScreen gameWin = new TitleScreen(conf, Assets.textures.winTex, 0){
            @Override
            public void render(float delta){
                super.render(delta);
                spriteBatch.begin();
                Assets.fonts.hudFont.draw(spriteBatch, "Score: " + GameScore.getInstance().getScore(), conf.VIEW_PORT_WIDTH, conf.VIEW_PORT_HEIGHT);
                spriteBatch.end();
            }
        };
        gameWin.bindings.bindNext(Input.Keys.ESCAPE);

		chain.add(new TitleScreen(conf, Assets.textures.logoTex, 1000));
        chain.add(startGame);
        chain.add(new GameScreen(conf));
        chain.add(gameWin);
        chain.add(gameOver);
		chain.gotoFrame(0);

	}
}
