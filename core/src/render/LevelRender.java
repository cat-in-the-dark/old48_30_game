package render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.level.Tile;

/**
 * Created by kirill on 23.08.14.
 */
public class LevelRender {

	private final SpriteBatch batch = new SpriteBatch();
	private final Camera camera;

	public LevelRender(Camera camera) {
		this.camera = camera;
	}

	public void render(Level level, float delta) {
		level.getWorld().step(delta, 6, 300);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Tile tile : level.tiles) {
			TextureRegion region;
			switch (tile.type) {
			case GRASS:
				region = Assets.textures.grass;
				break;
			case GRASS_SHADOW:
				region = Assets.textures.grass_shadow;
				break;
			case UNDERGROUND:
				region = Assets.textures.underground;
				break;
			case GRASS_SLOPE_LEFT:
				region = Assets.textures.grass_slope_slope;
				break;
			case GRASS_SLOPE_LEFT_SHADOW:
				region = Assets.textures.grass_slope_left_shadow;
				break;
			case GRASS_SLOPE_RIGHT:
				region = Assets.textures.grass_slope_right;
				break;
			case GRASS_SLOPE_RIGHT_SHADOW:
				region = Assets.textures.grass_slope_right_shadow;
				break;
			default:
				region = Assets.textures.empty;
				break;
			}
			batch.draw(region, tile.getX() * 32, tile.getY() * 32);
		}
		batch.end();
	}
}
