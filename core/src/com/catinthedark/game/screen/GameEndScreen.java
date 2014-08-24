package com.catinthedark.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.catinthedark.game.Config;
import com.catinthedark.game.GameScore;
import com.catinthedark.game.assets.Assets;

/**
 * Created by Ilya on 24.08.2014.
 */
public class GameEndScreen extends TitleScreen {
    public GameEndScreen(Config conf, Texture texture, int timeToLive) {
        super(conf, texture, timeToLive);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        spriteBatch.begin();
        Assets.fonts.hudFont.draw(spriteBatch, "Score: " + GameScore.getInstance().getScore(), conf.VIEW_PORT_WIDTH * conf.UNIT_SIZE - 160f, conf.VIEW_PORT_HEIGHT * conf.UNIT_SIZE - 70f);
        spriteBatch.end();
    }
}
