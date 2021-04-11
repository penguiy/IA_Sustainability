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


	@Override
	public void create () {
		batch = new SpriteBatch();
		hud = new Hud(this);
		currScreen = new GroundFloor(this);
		displaying = ScreenDisplay.GROUND;
		prevDisplayed = ScreenDisplay.GROUND;
		setScreen(currScreen);
		render();
	}

	@Override
	public void render () {
		super.render();
		handleInput();
		if (displaying != prevDisplayed) {
			currScreen.dispose();
			switch (displaying) {
				case STREET:
					currScreen = new StreetView(this);
					setScreen(currScreen);
					prevDisplayed = ScreenDisplay.STREET;
					break;
				case GROUND:
					currScreen = new GroundFloor(this);
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

	public String getData(){
		return this.data;
	}
	public void setDataLabel(String Data) {
		this.data = data;
	}

	//use to change the screen for outside classes
	public void changeScreen(ScreenDisplay displaying) {
		this.displaying = displaying;
	}

}
