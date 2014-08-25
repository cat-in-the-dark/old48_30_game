package com.catinthedark.game.level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.catinthedark.game.Constants;
import com.catinthedark.game.physics.PhysicsModel;

import entity.MushroomedCrab;

import java.util.*;

/**
 * Created by Ilya on 23.08.2014.
 */
public class TilePresetFactory {
	private final List<int[][]> rawTilePresetsEasy;
	private final List<int[][]> rawTilePresetsMedium;
	private final List<int[][]> rawTilePresetsHard;
	private final List<Vector2[]> botsPositionsEasy;
	private final List<Vector2[]> botsPositionsMedium;
	private final List<Vector2[]> botsPositionsHard;
	private final World world;

	private final Map<Integer, List<TilePreset>> tilePresets;

	public TilePresetFactory(World world) {
		this.world = world;
		this.rawTilePresetsEasy = new ArrayList<int[][]>() {
			{
				add(new int[][] {
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 0, 2, 1 },
						{ 0, 0, 6, 5 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 },
						{ 0, 2, 1, 7 }
				});
				add(new int[][] {
						{ 0, 2, 1, 7, 7, 7 },
						{ 0, 0, 4, 3, 7, 7 },
						{ 0, 0, 0, 4, 3, 7 },
						{ 0, 0, 0, 0, 4, 3 },
						{ 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 6, 5, 7, 7 },
						{ 0, 2, 1, 7, 7, 7 },
						{ 7, 7, 7, 7, 7, 7 },
						{ 7, 7, 7, 7, 7, 7 },
						{ 7, 7, 7, 7, 7, 7 },
						{ 7, 7, 7, 7, 7, 7 }
				});
				add(new int[][] {
						{ 0, 2, 1, 7, 7, 7, 7, 7, 7 },
						{ 0, 2, 1, 7, 7, 7, 7, 7, 7 },
						{ 0, 0, 4, 3, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 0, 4, 3, 7, 7, 7, 7 },
						{ 0, 0, 0, 0, 4, 3, 7, 7, 7 },
						{ 0, 0, 0, 0, 0, 4, 3, 7, 7 },
						{ 0, 0, 0, 0, 0, 0, 4, 3, 7 },
						{ 0, 0, 0, 0, 0, 0, 0, 4, 3 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 6, 5 },
						{ 0, 0, 0, 0, 0, 0, 6, 5, 7 },
						{ 0, 0, 0, 0, 0, 6, 5, 7, 7 },
						{ 0, 0, 0, 0, 6, 5, 7, 7, 7 },
						{ 0, 0, 0, 6, 5, 7, 7, 7, 7 },
						{ 0, 0, 6, 5, 7, 7, 7, 7, 7 },
				});
				add(new int[][] {
						{ 0, 0, 4, 3, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 2, 1, 7, 7, 7, 7, 7 },
						{ 0, 0, 0, 0, 0, 0, 0, 4, 3 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 2, 1 },
						{ 0, 0, 0, 0, 0, 0, 0, 6, 5 },
						{ 0, 0, 0, 0, 0, 0, 6, 5, 7 },
						{ 0, 0, 0, 0, 0, 6, 5, 7, 7 },
						{ 0, 0, 0, 0, 6, 5, 7, 7, 7 },
						{ 0, 0, 0, 6, 5, 7, 7, 7, 7 },
						{ 0, 0, 6, 5, 7, 7, 7, 7, 7 },
				});
			}
		};

		this.rawTilePresetsMedium = new ArrayList<int[][]>() {
			{
				add(new int[][] { { 1, 1, 1 }, { 2, 2, 2 } });
				add(new int[][] { { 1, 1, 1 }, { 2, 2, 2 }, { 0, 0, 0 } });
			}
		};
		this.rawTilePresetsHard = new ArrayList<int[][]>() {
			{
				add(new int[][] { { 1, 1, 1 }, { 2, 2, 2 } });
				add(new int[][] { { 1, 1, 1 }, { 2, 2, 2 }, { 0, 0, 0 } });
			}
		};

		this.botsPositionsEasy = new ArrayList<Vector2[]>() {
			{
				add(new Vector2[] { new Vector2(4, 4) });
				add(new Vector2[] { new Vector2(5, 7) });
				add(new Vector2[] { new Vector2(6, 7) });
				add(new Vector2[] { new Vector2(4, 4) });
			}
		};
		this.botsPositionsMedium = new ArrayList<Vector2[]>() {
			{

			}
		};
		this.botsPositionsHard = new ArrayList<Vector2[]>() {
			{
			}
		};

		this.tilePresets = new HashMap<Integer, List<TilePreset>>();
		this.tilePresets.put(Constants.EASY, new ArrayList<TilePreset>());
		this.tilePresets.put(Constants.MEDIUM, new ArrayList<TilePreset>());
		this.tilePresets.put(Constants.HARD, new ArrayList<TilePreset>());

		for (int[][] rawTilePreset : rawTilePresetsEasy) {
			tilePresets.get(Constants.EASY).add(
					new TilePreset(rawTilePreset, Constants.EASY,
							botsPositionsEasy));
		}

		for (int[][] rawTilePreset : rawTilePresetsMedium) {
			tilePresets.get(Constants.MEDIUM).add(
					new TilePreset(rawTilePreset, Constants.MEDIUM,
							botsPositionsMedium));
		}

		for (int[][] rawTilePreset : rawTilePresetsHard) {
			tilePresets.get(Constants.HARD).add(
					new TilePreset(rawTilePreset, Constants.HARD,
							botsPositionsHard));
		}
	}

	private MushroomedCrab createCrub(float posX, float posY) {
		// PolygonShape crubShape = new PolygonShape();
		CircleShape crubShape = new CircleShape();
		// crubShape.setAsBox(Constants.CRUB_WIDTH / 2, Constants.CRUB_HEIGHT /
		// 2);
		crubShape.setRadius(Constants.CRUB_WIDTH / 2);
		PhysicsModel crubModel = new PhysicsModel(world, posX, posY,
				crubShape,
				true, BodyDef.BodyType.DynamicBody, 1.0f);
		MushroomedCrab mush = new MushroomedCrab(crubModel);
		mush.moveLeft();
		// if (new Random().nextInt() % 2 == 0)
		// mush.moveRight();
		// else
		// mush.moveLeft();

		return mush;
	}

	public LevelPart build(int difficult, float presetX, float presetY) {
		LevelPart part = new LevelPart();
		if (tilePresets.get(difficult).size() == 0) {
			throw new RuntimeException(
					"There is no tile preset for this difficult "
							+ Integer.toString(difficult));
		}

		Random random = new Random();
		int i = random.nextInt(tilePresets.get(difficult).size());
		TilePreset tilePreset = tilePresets.get(difficult).get(i);
		List<Tile> tileList = new ArrayList<Tile>();
		for (Tile tile : tilePreset.tiles) {
			float newX = tile.getX() + presetX;
			float newY = tile.getY() + presetY;
			Tile newTile = new Tile(tile, newX, newY, world);
			tileList.add(newTile);
		}

		part.tiles = tileList;

		List<MushroomedCrab> crabs = new ArrayList<MushroomedCrab>();
		for (Vector2 pos : tilePreset.botsPositions.get(i))
			crabs.add(createCrub(presetX + pos.x, presetY + pos.y));

		part.crabs = crabs;

		return part;
	}
}
