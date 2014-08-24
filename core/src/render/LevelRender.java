package render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import com.catinthedark.game.level.Level;
import com.catinthedark.game.level.Tile;

import entity.Bullet;
import entity.MushroomedCrab;

/**
 * Created by kirill on 23.08.14.
 */
public class LevelRender {

	private final SpriteBatch batch = new SpriteBatch();
	private final Camera camera;
	private final Config conf;
	private final MushroomedCrabRender crabRender;
	private final MushShotRenderer bulletRenderer = new MushShotRenderer();

	public LevelRender(Config conf, Camera camera) {
		this.camera = camera;
		this.conf = conf;

		crabRender = new MushroomedCrabRender(conf);
	}

	public void render(Level level, float delta) {
		level.getWorld().step(delta, 6, 300);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// render tiles
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
			batch.draw(
					region,
					(tile.body.getPosition().x - Constants.BLOCK_WIDTH / 2) * 32,
					(tile.body.getPosition().y - Constants.BLOCK_HEIGHT / 2) * 32);
		}

		// render crabs
		for (MushroomedCrab crab : level.getCrabs())
			crabRender.render(crab, batch);

		// render bullets
		for (Bullet bullet : level.getBullets())
			bulletRenderer.render(batch, bullet);

		batch.end();
	}
}
