//Main Game Class
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Screens.Fade;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Screens.TitleScreen;
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Utils.InputListener;
import com.mygdx.game.Utils.SpriteManager;

import java.util.ArrayList;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen currScreen;
	private Hud hud;

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

	private Stage dayEnd;
	private Table table;

	public void setPlayer(Player player) {
		this.player = player;
	}

	private Player player;
	private Tile[][] layout;

	public Shop getShop() {
		return shop;
	}

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
		shop = new Shop(this);

		displaying = ScreenDisplay.STREET;
		prevDisplayed = ScreenDisplay.STREET;;
		currScreen = new Fade(this);
		//currScreen = titleScreen;
		setScreen(currScreen);
		render();
		inputMultiplexer = new InputMultiplexer();

		Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
		bgPixmap.setColor(0,0,0,0.8f);
		bgPixmap.fill();
		TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

		dayEnd = new Stage();
		table = new Table();
		table.setFillParent(true);
		table.setBackground(bg);
		TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
		style.font = new BitmapFont();

		TextButton save = new TextButton("SAVE", style);
		TextButton cont = new TextButton("CONTINUE", style);

		save.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("save");
				return false;
			}
		});
		cont.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				spriteManager.wipe();
				for (Flag flag: spriteManager.getFlagList()) {
					if(flag.isBodyDefined()) {
						flag.update();
					}
					if(flag.isClicked()){
						if(flag.getScreen().equals(ScreenDisplay.GROUND)) {
							groundFloor.getWorld().destroyBody(flag.getBody());
						}else if(flag.getScreen().equals(ScreenDisplay.STREET)){
							streetView.getWorld().destroyBody(flag.getBody());
						}
					}
				}
				spriteManager.setFlagList(new ArrayList<Flag>());
				displaying = ScreenDisplay.STREET;
				return false;
			}
		});
		table.add(cont).expandX().padBottom(10);
		table.row();
		table.add(save).expandX();
		dayEnd.addActor(table);

		inputMultiplexer.addProcessor(inputListener);
		inputMultiplexer.addProcessor(shop.getStageFirst());
		inputMultiplexer.addProcessor(shop.getStageClass());
		inputMultiplexer.addProcessor(shop.getStageInfra());
		inputMultiplexer.addProcessor(dayEnd);
		Gdx.input.setInputProcessor(inputMultiplexer);

	}

	@Override
	public void render(){
		super.render();
		if(displaying.equals(ScreenDisplay.PAUSE)){
			shop.getStageFirst().act();
			shop.getStageClass().act();
			shop.getStageInfra().act();
			shop.getStage().draw();

		} else if(displaying.equals(ScreenDisplay.DAYEND)){
			dayEnd.draw();
		}
		if (displaying != prevDisplayed) {
			if (!(prevDisplayed.equals(ScreenDisplay.PAUSE) || displaying.equals(ScreenDisplay.PAUSE))){
				currScreen.dispose();
				currScreen = new Fade(this);
			}
			setScreen(currScreen);
			prevDisplayed = displaying;
		}
		spriteManager.flagRaise();
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
