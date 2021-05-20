package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Screens.Fade;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Sprites.TempSprite;
import com.mygdx.game.Utils.SpriteManager;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen currScreen;
	private Hud hud;

	private boolean dayEnd;

	private ScreenDisplay displaying;
	private ScreenDisplay prevDisplayed;

	private GameState gameState;

	private SpriteManager spriteManager;

	//Screens
	private GroundFloor groundFloor;
	private StreetView streetView;

	private Player player;
	private Tile[][] layout;


	@Override
	public void create () {
		batch = new SpriteBatch();
		hud = new Hud(this);

		spriteManager = new SpriteManager(this, hud);
		groundFloor = new GroundFloor(this);
		streetView = new StreetView(this);

		displaying = ScreenDisplay.STREET;
		prevDisplayed = ScreenDisplay.STREET;;
		currScreen = new Fade(this);
		player = new Player();
		//currScreen = streetView;
		setScreen(currScreen);

		render();
	}

	@Override
	public void render(){
		super.render();
		spriteManager.flagRaise();
		if (displaying != prevDisplayed) {
			currScreen.dispose();
			currScreen = new Fade(this);
			setScreen(currScreen);
			switch (displaying) {
				case STREET:
					prevDisplayed = ScreenDisplay.STREET;
					break;
				case GROUND:
					prevDisplayed = ScreenDisplay.GROUND;
					break;
			}
		}
	}
	/**
	 * Creates a 2D array of Cells from the TiledMap and stores it in layout
	 * @param map
	 */
	public void mapOut(TiledMap map) {

		this.layout = new Tile[24][35];
		TiledMapTileLayer passable = (TiledMapTileLayer) map.getLayers().get(Con.PASSABLE_STRING);
		int colCount = 0;
		for (int y = 0; y < 24; y++) {
			for (int x = 0; x < 35; x++) {
				TiledMapTileLayer.Cell currCell = passable.getCell(x, 23 - y);
				colCount++;
				if (currCell == null) {
					System.out.print(" X ");
					layout[y][x] = new Tile(true, x, y);
				} else {
					System.out.print(" - ");
					layout[y][x] = new Tile(false, currCell.getTile(), x, y);
				}
				if (colCount == 35) {
					System.out.print("\n");
					colCount = 0;
				}
			}
		}
	}
	public SpriteBatch getBatch(){
		return batch;
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public Hud getHud() {
		return hud;
	}

	public boolean isDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(boolean dayEnd) {
		this.dayEnd = dayEnd;
	}

	public ScreenDisplay getDisplaying() {
		return displaying;
	}

	public ScreenDisplay getPrevDisplayed() {
		return prevDisplayed;
	}


	//use to change the screen for outside classes
	public void changeScreen(ScreenDisplay displaying) {
		this.displaying = displaying;
	}

	public GroundFloor getGroundFloor() {
		return groundFloor;
	}

	public StreetView getStreetView() {
		return streetView;
	}

	public SpriteManager getSpriteManager() {
		return spriteManager;
	}


	public Player getPlayer() {
		return player;
	}


	public Tile[][] getLayout() {
		return layout;
	}
}
