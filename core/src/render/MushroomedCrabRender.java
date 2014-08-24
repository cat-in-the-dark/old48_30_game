package render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import entity.Entity;

/**
 * Created by Ilya on 24.08.2014.
 */
public class MushroomedCrabRender {
	private Config conf;

	public MushroomedCrabRender(Config conf) {
		this.conf = conf;
	}

	public void render(Entity crab, SpriteBatch batch) {
		Vector2 entityPos = crab.getBody().getPosition();
		float posX = entityPos.x - Constants.MUSHROOMED_CRAB_WIDTH / 2;
		float posY = entityPos.y - Constants.MUSHROOMED_CRAB_HEIGHT / 2;

		batch.draw(
				playAnimation(crab),
				posX * conf.UNIT_SIZE,
				posY * conf.UNIT_SIZE,
				Constants.MUSHROOMED_CRAB_WIDTH * conf.UNIT_SIZE,
				Constants.MUSHROOMED_CRAB_HEIGHT * conf.UNIT_SIZE);
	}

	private TextureRegion playAnimation(Entity crab) {
		float stateTime = crab.getStateTime();

		if (crab.isMoving()) {
			switch (crab.getDirX()) {
			case RIGHT:
				return Assets.animations.mushroomedCrabRunRight
						.getKeyFrame(stateTime);
			case LEFT:
				return Assets.animations.mushroomedCrabRunLeft
						.getKeyFrame(stateTime);
			}
		} else {
            if (crab.isShutting()) {
                switch (crab.getDirX()) {
                    case RIGHT:
                        return Assets.animations.mushroomedCrabFireRight.getKeyFrame(stateTime);
                    case LEFT:
                        return Assets.animations.mushroomedCrabFireLeft.getKeyFrame(stateTime);
                }
            } else {
                switch (crab.getDirX()) {
                    case RIGHT:
                        return Assets.animations.mushroomedCrabIdleRight
                                .getKeyFrame(stateTime);
                    case LEFT:
                        return Assets.animations.mushroomedCrabIdleLeft
                                .getKeyFrame(stateTime);
                }
            }
		}

		return Assets.animations.mushroomedCrabRunLeft.getKeyFrame(stateTime);
	}
}
