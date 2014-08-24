package render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import entity.Block;

import java.util.List;

/**
 * Created by kirill on 23.08.14.
 */
public class BlocksRender {
    private final Config conf;
    private final OrthographicCamera camera;
    private final SpriteBatch batch = new SpriteBatch();

    public BlocksRender(Config conf, OrthographicCamera camera) {
        this.conf = conf;
        this.camera = camera;
    }

    public void render(List<Block> blocks) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Block block : blocks) {
            render(block);
        }
        batch.end();
    }

    private void render(Block block) {
        Vector2 blockPos = block.getBody().getPosition();
        batch.draw(Assets.textures.underground, (blockPos.x - Constants.BLOCK_WIDTH / 2) * conf.UNIT_SIZE,
                (blockPos.y - Constants.BLOCK_HEIGHT / 2) * conf.UNIT_SIZE,
                Constants.BLOCK_WIDTH * conf.UNIT_SIZE, Constants.BLOCK_HEIGHT * conf.UNIT_SIZE);
    }
}
