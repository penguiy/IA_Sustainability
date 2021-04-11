package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;

public class Main extends Game {
	private SpriteBatch batch;
	private Texture img;
	private Screen currScreen;
	private Hud hud;

	private boolean dayEnd;
	private ScreenDisplay displaying;
	private ScreenDisplay prevDisplayed;

	private String mousePos;
	private Vector3 touchPos;
	private OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		hud = new Hud(this);
		currScreen = new GroundFloor(this);
		displaying = ScreenDisplay.GROUND;
		prevDisplayed = ScreenDisplay.GROUND;
		touchPos = new Vector3();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Con.WIDTH, Con.HEIGHT);
		setScreen(currScreen);
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
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(touchPos);
			//Add an condition to check if it is over the sprite
			mousePos = touchPos.x +"," + touchPos.y;
			if(displaying == ScreenDisplay.GROUND){
				displaying = ScreenDisplay.STREET;
			}
			else{
				displaying = ScreenDisplay.GROUND;
			}
		}
	}

	public SpriteBatch getBatch(){
		return batch;
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
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

	public String getMousePos(){
		return this.mousePos;
	}

	//use to change the screen for outside classes
	public void changeScreen(ScreenDisplay displaying) {
		this.displaying = displaying;
	}

	public void setDisplaying(ScreenDisplay displaying) {
		this.displaying = displaying;
	}

	public void setPrevDisplayed(ScreenDisplay prevDisplayed) {
		this.prevDisplayed = prevDisplayed;
	}
}
