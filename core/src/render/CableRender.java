package render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;
import entity.Cable;

import java.util.Arrays;

/**
 * Created by kirill on 24.08.14.
 */
public class CableRender {
    private final Config conf;
    private final Vector2[] points = new Vector2[Constants.CABLE_STEPS];
    private ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;

    public CableRender(Config conf, OrthographicCamera camera) {
        this.conf = conf;
        shapeRenderer = new ShapeRenderer();
        this.camera = camera;
        shapeRenderer.setColor(0.9f, 0.65f, 0.18f, 0.5f);
    }

    public void render(Cable cable) {

        Vector2[] dataSet = new Vector2[cable.getBodyList().size()];
        /* fill dataSet with path points */
        for (int i = 0; i < cable.getBodyList().size(); i++) {
            Vector2 cablePosition = cable.getBodyList().get(i).getPosition();
            dataSet[i] = new Vector2(cablePosition.x * conf.UNIT_SIZE, cablePosition.y * conf.UNIT_SIZE);
        }

        CatmullRomSpline<Vector2> romSpline = new CatmullRomSpline<Vector2>(dataSet, false);

        for (int i = 0; i < Constants.CABLE_STEPS; i++) {
            points[i] = new Vector2();
            romSpline.valueAt(points[i], ((float) i) / ((float) Constants.CABLE_STEPS - 1));
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(int i = 0; i < Constants.CABLE_STEPS - 1; ++i)
        {
            Gdx.gl20.glLineWidth(Constants.CABLE_THICK);
            shapeRenderer.line(points[i], points[i + 1]);
        }
        shapeRenderer.end();
    }
}
