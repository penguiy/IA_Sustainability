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
	SpriteBatch batch;
	Texture img;
	Screen currScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
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
}
