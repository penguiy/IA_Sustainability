package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Con;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;

public class Fade implements Screen {
    private Main game;
    private ShapeRenderer shapeRenderer;
    private ScreenDisplay to;
    private float totalDeltaTime;
    private boolean fadeIn;
    public Fade(Main game){
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        to = game.getDisplaying();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(totalDeltaTime < 1 && !fadeIn){
            totalDeltaTime += delta;
        }
        else if(totalDeltaTime >= 0) {
            fadeIn = true;
            totalDeltaTime -= delta;
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            switch(to) {
                case GROUND:
                    game.getGroundFloor().render(delta);
                    break;
                case STREET:
                    game.getStreetView().render(delta);
                    break;
            }

        }
        else{
            switch (to){
                case GROUND:
                    game.setScreen(game.getGroundFloor());
                    break;
                case STREET:
                    game.setScreen(game.getStreetView());
                    break;
            }
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, totalDeltaTime);
        shapeRenderer.rect(0, 0, Con.WIDTH * 2, Con.HEIGHT * 2); // Need to change number to account for viewport size
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
