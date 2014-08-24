package render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;

import entity.DirectionX;
import entity.Player;

public class PlayerRender {
	private final Config conf;
	private final SpriteBatch batch = new SpriteBatch();

	public PlayerRender(Config conf) {
		this.conf = conf;
	}

	public void render(Player player) {
		batch.begin();

		Vector2 playerPos = player.getBody().getPosition();
		TextureRegion[][] frames;
		Animation jumpAnimation;
		Animation goAnimation;

		if (player.getDirX() == DirectionX.RIGHT) {
			frames = Assets.textures.playerFrames;
			jumpAnimation = Assets.animations.playerJump;
			goAnimation = Assets.animations.playerGo;
		}
		else {
			frames = Assets.textures.playerFramesBack;
			jumpAnimation = Assets.animations.playerJumpBack;
			goAnimation = Assets.animations.playerGoBack;
		}

		if (player.isMooving()) {
			batch.draw(
					goAnimation.getKeyFrame(player
							.getStateTime()),
					(playerPos.x - Constants.PLAYER_HEIGHT / 2)
							* conf.UNIT_SIZE,
					(playerPos.y - Constants.PLAYER_WIDTH / 2) * conf.UNIT_SIZE,
					Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
					Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
			batch.end();
			return;
		}

		if (player.isStay()) {

			TextureRegion frame = null;
			// если стоит
			switch (player.getDirY()) {
			case CROSSHAIR_UP:
				frame = frames[0][12];
				break;
			case CROSSHAIR_MIDDLE:
				frame = frames[0][11];
				break;
			case CROSSHAIR_DOWN:
				frame = frames[0][13];
				break;

			}

			batch.draw(
					frame,
					(playerPos.x - Constants.PLAYER_HEIGHT / 2)
							* conf.UNIT_SIZE,
					(playerPos.y - Constants.PLAYER_WIDTH / 2) * conf.UNIT_SIZE,
					Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
					Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
		} else {// летим
			batch.draw(
					jumpAnimation.getKeyFrame(player
							.getStateTime()),
					(playerPos.x - Constants.PLAYER_HEIGHT / 2)
							* conf.UNIT_SIZE,
					(playerPos.y - Constants.PLAYER_WIDTH / 2) * conf.UNIT_SIZE,
					Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
					Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
		}

		// draw shot
		if (player.isInAttack()) {
			switch (player.getDirY()) {
			case CROSSHAIR_UP:
				batch.draw(Assets.textures.shot,
						(playerPos.x + Constants.PLAYER_HEIGHT / 2)
								* conf.UNIT_SIZE,
						(playerPos.y - Constants.PLAYER_WIDTH / 2)
								* conf.UNIT_SIZE + 16, 0, 0,
						256, 32, 1, 1, 45, 0, 0, 1, 1, false, false);
				break;
			case CROSSHAIR_MIDDLE:
				batch.draw(
						Assets.textures.shot,
						(playerPos.x + Constants.PLAYER_HEIGHT / 2)
								* conf.UNIT_SIZE,
						(playerPos.y - Constants.PLAYER_WIDTH / 2)
								* conf.UNIT_SIZE
								+ 16,
						256, 32);
				break;
			case CROSSHAIR_DOWN:
				batch.draw(Assets.textures.shot,
						(playerPos.x + Constants.PLAYER_HEIGHT / 2)
								* conf.UNIT_SIZE,
						(playerPos.y - Constants.PLAYER_WIDTH / 2)
								* conf.UNIT_SIZE + 16, 0, 0,
						256, 32, 1, 1, -45, 0, 0, 1, 1, false, false);
				break;
			}
		}

		batch.end();
	}
}
