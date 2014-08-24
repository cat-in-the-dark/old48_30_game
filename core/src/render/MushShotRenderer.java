package render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.game.assets.Assets;

import entity.Bullet;

public class MushShotRenderer {
	public void render(SpriteBatch batch, Bullet bullet) {
		batch.draw(Assets.textures.mushroom,
				bullet.getBody().getPosition().x * 32, bullet.getBody()
						.getPosition().y * 32, 16, 16);
	}
}
