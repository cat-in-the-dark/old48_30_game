package render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.game.Config;
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

		batch.end();
	}

}
