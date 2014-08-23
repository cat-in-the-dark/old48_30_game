package render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.game.Config;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.hud.Hud;

public class HudRenderer {
	private final SpriteBatch batch = new SpriteBatch();
	private final Config conf;

	public HudRenderer(Config conf) {
		this.conf = conf;
	}

	public void render(Hud hud) {
		batch.begin();
		Assets.textures.heartReg.setU(hud.getHealth() + 1);
		batch.draw(Assets.textures.heartReg, conf.HUD_LEFT, conf.HUD_TOP,
				hud.getHealth()
						* conf.UNIT_SIZE / 2,
				conf.UNIT_SIZE / 2);

		float u = 0.9f - (float) hud.getMeters() / 100;
		if (u < 0)
			u = 0;

		float width = (float) hud.getMeters() / 10 * 9 / 10 * conf.UNIT_SIZE
				/ 2 + conf.UNIT_SIZE / 2;
		if (width > conf.UNIT_SIZE / 2 * 10)
			width = conf.UNIT_SIZE / 2 * 10;

		Assets.textures.hudWireReg.setU(u);
		Assets.textures.hudWireReg.setU2(1.0f);
		batch.draw(Assets.textures.hudWireReg, conf.HUD_LEFT,
				conf.HUD_TOP - 30, width,
				conf.UNIT_SIZE / 2);

		Assets.fonts.hudFont.draw(batch, "Score: " + hud.getScore(),
				conf.HUD_LEFT, conf.HUD_TOP - 50);

		batch.end();

	}

	public void dispose() {
		batch.dispose();
	}
}
