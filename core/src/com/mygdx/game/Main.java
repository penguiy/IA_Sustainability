//Main Game Class
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Screens.Fade;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Screens.TitleScreen;
import com.mygdx.game.Utils.InputListener;
import com.mygdx.game.Utils.SpriteManager;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen currScreen;
	private Hud hud;

	private boolean dayEnd;

	private ScreenDisplay displaying;
	private ScreenDisplay prevDisplayed;


	private SpriteManager spriteManager;

	public InputListener getInputListener() {
		return inputListener;
	}
	private InputListener inputListener;
	private InputMultiplexer inputMultiplexer;

	//Screens
	private GroundFloor groundFloor;
	private StreetView streetView;
	private TitleScreen titleScreen;

	public void setPlayer(Player player) {
		this.player = player;
	}

	private Player player;
	private Tile[][] layout;

	private Shop shop;

	public boolean inTransition() {
		return transition;
	}

	public void setTransition(boolean transition) {
		this.transition = transition;
	}

	private boolean transition;


	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player();
		hud = new Hud(this);

		spriteManager = new SpriteManager(this, hud);
		inputListener = new InputListener(this);
		groundFloor = new GroundFloor(this);
		streetView = new StreetView(this);
		titleScreen = new TitleScreen(this);
		shop = new Shop();

		displaying = ScreenDisplay.STREET;
		prevDisplayed = ScreenDisplay.STREET;;
		currScreen = new Fade(this);
		//currScreen = titleScreen;
		setScreen(currScreen);
		render();
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);

		Gdx.input.setInputProcessor(inputListener);
		inputMultiplexer.addProcessor(shop.getStage());
	}

	@Override
	public void render(){
		super.render();
		spriteManager.flagRaise();
		if (displaying != prevDisplayed) {
			if (!(prevDisplayed.equals(ScreenDisplay.PAUSE) || displaying.equals(ScreenDisplay.PAUSE))){
				currScreen.dispose();
				currScreen = new Fade(this);
			}
			setScreen(currScreen);
			prevDisplayed = displaying;
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
