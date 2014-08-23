package render;

import com.catinthedark.game.level.Level;

/**
 * Created by kirill on 23.08.14.
 */
public class LevelRender {
    public void render(Level level, float delta) {
        level.getWorld().step(delta, 6, 2);
    }
}
