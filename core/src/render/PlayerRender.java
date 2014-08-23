package render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;

import entity.Player;

public class PlayerRender {
	private final Config conf;
	private final SpriteBatch batch = new SpriteBatch();

	public PlayerRender(Config conf) {
		this.conf = conf;
	}

	public void render(Player player) {
		batch.begin();

		switch (player.getDirX()) {
		case LEFT:
			Assets.fonts.hudFont.draw(batch, "left", 200, 200);
			break;

		case RIGHT:
			Assets.fonts.hudFont.draw(batch, "right", 200, 200);
			break;
		}

		switch (player.getDirY()) {
		case CROSSHAIR_UP:
			Assets.fonts.hudFont.draw(batch, "cross up", 200, 230);
			break;

		case CROSSHAIR_MIDDLE:
			Assets.fonts.hudFont.draw(batch, "cross middle", 200, 230);
			break;
		case CROSSHAIR_DOWN:
			Assets.fonts.hudFont.draw(batch, "cross down", 200, 230);
			break;
		}

		Vector2 playerPos = player.getBody().getPosition();
		batch.draw(Assets.textures.playerReg,
				(playerPos.x - Constants.PLAYER_HEIGHT / 2) * conf.UNIT_SIZE,
				(playerPos.y - Constants.PLAYER_WIDTH / 2) * conf.UNIT_SIZE,
				Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
				Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
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
