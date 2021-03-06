package render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.game.Config;
import com.catinthedark.game.GameScore;
import com.catinthedark.game.assets.Assets;

public class HudRenderer {
	private final SpriteBatch batch = new SpriteBatch();
	private final Config conf;

	public HudRenderer(Config conf) {
		this.conf = conf;
	}

	public void render() {
        GameScore score = GameScore.getInstance();

		batch.begin();
		Assets.textures.heartReg.setU(score.getHealth() + 1);
		batch.draw(Assets.textures.heartReg, conf.HUD_LEFT, conf.HUD_TOP,
				score.getHealth()
						* conf.UNIT_SIZE / 2,
				conf.UNIT_SIZE / 2);

		float u = 0.9f - (float) score.getMeters() / 100;
		if (u < 0)
			u = 0;

		float width = (float) score.getMeters() / 10 * 9 / 10 * conf.UNIT_SIZE
				/ 2 + conf.UNIT_SIZE / 2;
		if (width > conf.UNIT_SIZE / 2 * 10)
			width = conf.UNIT_SIZE / 2 * 10;

		Assets.textures.hudWireReg.setU(u);
		Assets.textures.hudWireReg.setU2(1.0f);
		batch.draw(Assets.textures.hudWireReg, conf.HUD_LEFT,
				conf.HUD_TOP - 30, width,
				conf.UNIT_SIZE / 2);

		Assets.fonts.hudFont.draw(batch, "Score: " + score.getScore(),
				conf.HUD_LEFT, conf.HUD_TOP - 50);

		Assets.fonts.hudFont.draw(batch, "Level: " + score.getLevel(),
				conf.HUD_LEFT, conf.HUD_TOP - 80);
		batch.end();

	}

	public void dispose() {
		batch.dispose();
	}
}
