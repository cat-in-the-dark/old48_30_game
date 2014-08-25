package render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.game.Config;
import com.catinthedark.game.Constants;
import com.catinthedark.game.assets.Assets;

import entity.DirectionX;
import entity.Player;

public class PlayerRender {
	private final Config conf;
	private final SpriteBatch batch = new SpriteBatch();
	private final OrthographicCamera camera;
    private boolean blink = false;
    private int blinkCount = 0;


    private int wifiRayOffset = 0;

	public PlayerRender(Config conf, OrthographicCamera camera) {
		this.conf = conf;
		this.camera = camera;
	}

	public void render(Player player) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		Vector2 playerPos = player.getBody().getPosition();
		TextureRegion[][] frames;
		Animation jumpAnimation;
		Animation goAnimation;

		if (player.getDirX() == DirectionX.RIGHT) {
			frames = Assets.textures.playerFrames;
			jumpAnimation = Assets.animations.playerJump;
			goAnimation = Assets.animations.playerGo;
		}
		else {
			frames = Assets.textures.playerFramesBack;
			jumpAnimation = Assets.animations.playerJumpBack;
			goAnimation = Assets.animations.playerGoBack;
		}

        if (!player.isDamaged() || blink) {
            if (player.isMooving()) {
                batch.draw(
                        goAnimation.getKeyFrame(player
                                .getStateTime()),
                        (playerPos.x - Constants.PLAYER_HEIGHT / 2)
                                * conf.UNIT_SIZE,
                        (playerPos.y - Constants.PLAYER_WIDTH / 2) * conf.UNIT_SIZE,
                        Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
                        Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
            } else {

                if (player.isOnGround()) {

                    TextureRegion frame = null;
                    // если стоит
                    switch (player.getDirY()) {
                        case CROSSHAIR_UP:
                            frame = frames[0][13];
                            break;
                        case CROSSHAIR_MIDDLE:
                            frame = frames[0][12];
                            break;
                        case CROSSHAIR_DOWN:
                            frame = frames[0][14];
                            break;

                    }

                    batch.draw(
                            frame,
                            (playerPos.x - Constants.PLAYER_HEIGHT / 2)
                                    * conf.UNIT_SIZE,
                            (playerPos.y - Constants.PLAYER_WIDTH / 2)
                                    * conf.UNIT_SIZE,
                            Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
                            Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
                } else {// летим
                    batch.draw(
                            jumpAnimation.getKeyFrame(player
                                    .getStateTime()),
                            (playerPos.x - Constants.PLAYER_HEIGHT / 2)
                                    * conf.UNIT_SIZE,
                            (playerPos.y - Constants.PLAYER_WIDTH / 2)
                                    * conf.UNIT_SIZE,
                            Constants.PLAYER_WIDTH * conf.UNIT_SIZE,
                            Constants.PLAYER_HEIGHT * conf.UNIT_SIZE);
                }
            }
        } else {
            blinkCount++;
        }
        if (blinkCount >= 20) {
            blinkCount = 0;
            player.setDamaged(false);
        }
        blink = !blink;

		// draw shot
		if (player.isInAttack()) {
            wifiRayOffset += 4;
            if (wifiRayOffset > Assets.textures.shot.getWidth() / 2) {
                wifiRayOffset = 0;
            }
            if (player.getDirX() == DirectionX.RIGHT) {
                switch (player.getDirY()) {
                case CROSSHAIR_UP:
                    batch.draw(Assets.textures.shot,
                            (playerPos.x + Constants.PLAYER_HEIGHT / 2)
                                    * conf.UNIT_SIZE + 3,
                            (playerPos.y - Constants.PLAYER_WIDTH / 2)
                                    * conf.UNIT_SIZE + 38,
                            0 , 0, 256, 32, 1, 1, 45,
                            256 - wifiRayOffset,
                            0, 256, 32, false, false);
                    break;
				case CROSSHAIR_MIDDLE:
					batch.draw(
							Assets.textures.shot,
							(playerPos.x + Constants.PLAYER_HEIGHT / 2)
									* conf.UNIT_SIZE,
							(playerPos.y - Constants.PLAYER_WIDTH / 2)
									* conf.UNIT_SIZE + 20,
                            0, 0, 256, 32, 1, 1, 0,
                            256 - wifiRayOffset,
                            0, 256, 32, false, false);
					break;
				case CROSSHAIR_DOWN:
					batch.draw(Assets.textures.shot,
							(playerPos.x + Constants.PLAYER_HEIGHT / 2)
									* conf.UNIT_SIZE - 15,
							(playerPos.y - Constants.PLAYER_WIDTH / 2)
									* conf.UNIT_SIZE + 5,
                            0, 0, 256, 32, 1, 1, -45,
                            256 - wifiRayOffset,
                            0, 256, 32, false, false);
					break;
				}
			} else {
				switch (player.getDirY()) {
				case CROSSHAIR_UP:
					batch.draw(Assets.textures.shot,
							(playerPos.x - Constants.PLAYER_HEIGHT / 2)
									* conf.UNIT_SIZE - 5,
							(playerPos.y - Constants.PLAYER_WIDTH / 2)
									* conf.UNIT_SIZE + 42,
                            0 , 0, -256, 32, 1, 1, -45,
                            256 - wifiRayOffset,
                            0, 256, 32, false, false);
					break;
				case CROSSHAIR_MIDDLE:
					batch.draw(
							Assets.textures.shot,
							(playerPos.x - Constants.PLAYER_HEIGHT / 2)
									* conf.UNIT_SIZE,
							(playerPos.y - Constants.PLAYER_WIDTH / 2)
									* conf.UNIT_SIZE + 20,
                            0, 0, -256, 32, 1, 1, 0,
                            256 - wifiRayOffset,
                            0, 256, 32, false, false);
					break;
				case CROSSHAIR_DOWN:
					batch.draw(Assets.textures.shot,
							(playerPos.x - Constants.PLAYER_HEIGHT / 2)
									* conf.UNIT_SIZE + 13,
							(playerPos.y - Constants.PLAYER_WIDTH / 2)
									* conf.UNIT_SIZE + 3,
                            0, 0, -256, 32, 1, 1, 45,
                            256 - wifiRayOffset, 0, 256, 32, false, false);
					break;
				}
			}
        } else {
            wifiRayOffset = 0;
        }

		batch.end();
	}
}
