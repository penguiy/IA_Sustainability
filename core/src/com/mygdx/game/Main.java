package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Screens.Fade;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen currScreen;
	private Hud hud;

	private boolean dayEnd;

	private ScreenDisplay displaying;
	private ScreenDisplay prevDisplayed;

	private String data;

	//Screens
	private GroundFloor groundFloor;
	private StreetView streetView;


	@Override
	public void create () {
		batch = new SpriteBatch();
		hud = new Hud(this);
		groundFloor = new GroundFloor(this);
		streetView = new StreetView(this);
		displaying = ScreenDisplay.GROUND;
		prevDisplayed = ScreenDisplay.GROUND;
		currScreen = new Fade(this);
		setScreen(currScreen);
		render();
	}

	@Override
	public void render(){
		super.render();
		handleInput();
		if (displaying != prevDisplayed) {
			currScreen.dispose();
			currScreen = new Fade(this);
			switch (displaying) {
				case STREET:
					setScreen(currScreen);
					prevDisplayed = ScreenDisplay.STREET;
					break;
				case GROUND:
					setScreen(currScreen);
					prevDisplayed = ScreenDisplay.GROUND;
					break;
			}
		}
	}
	private void handleInput(){
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
}
