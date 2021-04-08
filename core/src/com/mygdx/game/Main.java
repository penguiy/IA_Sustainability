package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.GroundFloor;

public class Main extends Game {
	private SpriteBatch batch;
	private Texture img;
	private Screen currScreen;
	private Hud hud;

	private boolean dayEnd;


	@Override
	public void create () {
		batch = new SpriteBatch();
		hud = new Hud(this);
		currScreen = new GroundFloor(this);
		setScreen(currScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
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
}
