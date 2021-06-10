//Main Game Class
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Screens.BaseScreen;
import com.mygdx.game.Screens.Fade;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Screens.TitleScreen;
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Utils.InputListener;
import com.mygdx.game.Utils.SpriteManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen currScreen;

	public TextureAtlas getAtlas() {
		return atlas;
	}

	private TextureAtlas atlas;
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

	public TitleScreen getTitleScreen() {
		return titleScreen;
	}

	private TitleScreen titleScreen;

	private Stage dayEnd;
	private Stage errorNotif;

	public Label getErrorLabel() {
		return errorLabel;
	}

	private Label errorLabel;
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

	public Preferences getPref() {
		return pref;
	}

	private Preferences pref;
	private float fadeDelta;

	@Override
	public void create () {
		pref = Gdx.app.getPreferences(Con.SAVE_FILE);
		batch = new SpriteBatch();
		fadeDelta = 0;
		atlas = new TextureAtlas(Con.ATLAS_FILENAME);
		errorNotif = new Stage();
		errorLabel = new Label("INVALID INPUT", new Label.LabelStyle(new BitmapFont(), Color.RED));
		errorLabel.setColor(1,0,0,0);
		errorLabel.layout();
		errorLabel.setPosition(Con.WIDTH/2-8,Con.HEIGHT/5);
		errorLabel.setSize(200,100);
		errorLabel.setFillParent(true);
		errorNotif.addActor(errorLabel);

		if(pref.getBoolean(Con.FILE_EXISTS)){
			setPlayer(this.loadSave());
		}
		else{
			player = new Player();
		}
		hud = new Hud(this);

		spriteManager = new SpriteManager(this, hud);
		inputListener = new InputListener(this);
		groundFloor = new GroundFloor(this);
		streetView = new StreetView(this);
		titleScreen = new TitleScreen(this);
		shop = new Shop(this);

		displaying = ScreenDisplay.TITLE;
		prevDisplayed = ScreenDisplay.TITLE;;
		//currScreen = new Fade(this);
		currScreen = titleScreen;
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
		style.up = new TextureRegionDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Con.BUTTONG_BG))));


		TextButton save = new TextButton("SAVE & EXIT", style);
		TextButton cont = new TextButton("CONTINUE", style);

		save.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pref.putBoolean(Con.FILE_EXISTS, true);
				pref.putInteger(Con.DAY_NUM, player.getDayNum());
				pref.putInteger(Con.POINTS, player.getPoints());
				pref.putString(Con.ODDS, H2S(player.getOdds()));
				pref.putString(Con.MULTI, H2S(player.getMulti()));
				pref.putString(Con.CLASS_COUNT, H2S(player.getClassCount()));
				pref.putString(Con.INFRA_COUNT, H2S(player.getInfraCount()));
				pref.putString(Con.INFRA_PURCHASE, player.getInfraPurchase().toString().substring(1,
						player.getInfraPurchase().toString().length()-1));
				pref.flush();
				Gdx.app.exit();
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
		inputMultiplexer.addProcessor(titleScreen.getStage());
		inputMultiplexer.addProcessor(dayEnd);

		Gdx.input.setInputProcessor(inputMultiplexer);

	}

	@Override
	public void render(){
		super.render();
		if(displaying.equals(ScreenDisplay.PAUSE) && !displaying.equals(ScreenDisplay.TITLE)){
			this.toggleDayEnd(false);
			shop.getStageFirst().act();
			shop.getStageClass().act();
			shop.getStageInfra().act();
			shop.getStage().draw();
		} else if(displaying.equals(ScreenDisplay.DAYEND)) {
			shop.toggleShop(false);
			this.toggleDayEnd(true);
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
		errorNotif.draw();
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

	public Player loadSave(){
		int day = pref.getInteger(Con.DAY_NUM);
		int point = pref.getInteger(Con.POINTS);
		HashMap<String, Integer> odds = reconHashInt(pref.getString(Con.ODDS));
		HashMap<String, Double> multi = reconHashDouble(pref.getString(Con.MULTI));
		HashMap<String, Integer> classCount = reconHashInt(pref.getString(Con.CLASS_COUNT));
		HashMap<String, Integer> infraCount = reconHashInt(pref.getString(Con.INFRA_COUNT));
		ArrayList<String> purchases = reconArrayList(pref.getString(Con.INFRA_PURCHASE));
		Player save = new Player(point,day,odds,multi,classCount,infraCount,purchases);
		return save;
	}

	public HashMap<String, Double> reconHashDouble(String str){
		HashMap<String,Double> TBR = new HashMap<String,Double>();
		String[] list = str.split(", ");
		for(String string : list){
			String[] keyVal = string.split("=");
			TBR.put(keyVal[0], Double.parseDouble(keyVal[1]));
		}
		return TBR;
	}
	public HashMap<String, Integer> reconHashInt(String str){
		HashMap<String,Integer> TBR = new HashMap<String,Integer>();
		String[] list = str.split(", ");
		for(String string : list){
			String[] keyVal = string.split("=");
			TBR.put(keyVal[0], Integer.parseInt(keyVal[1]));
		}
		return TBR;
	}
	public ArrayList<String> reconArrayList(String str){
		String[] list = str.split(", ");
		ArrayList<String> TBR = new ArrayList<String>(Arrays.asList(list));
		return TBR;
	}
	public String H2S(HashMap hm){
		return hm.toString().substring(1,hm.toString().length()-1);
	}

	public void deleteSave(){
		if(pref.getBoolean(Con.FILE_EXISTS)) {
			pref.clear();
			pref.flush();
		}
	}
	public void showError(){
		errorLabel.setColor(1, 0, 0, 1);
	}
	public void fadeError(float delta){
		if(errorLabel.getColor().a == 0){
			fadeDelta = 0;
		}
		else{
			fadeDelta -= delta;
			errorLabel.setColor(1,0,0,errorLabel.getColor().a - delta);
		}
	}
	public void toggleDayEnd(boolean on){
		Touchable touchable;
		if(on){
			touchable = Touchable.enabled;
		} else{
			touchable = Touchable.disabled;
		}

		for (Actor actor: dayEnd.getActors()) {
			actor.setTouchable(touchable);
		}
	}
}
