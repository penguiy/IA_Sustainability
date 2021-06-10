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
import com.mygdx.game.Sprites.Flag;

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
            //When block is not solid decrease transparency
            totalDeltaTime += delta;
        }
        else{
            fadeIn = true;
            //When block is solid increase transparency
            totalDeltaTime -= delta;
            switch (to) {
                case GROUND:
                    game.getGroundFloor().render(delta);
                    break;
                case STREET:
                    game.getStreetView().render(delta);
                    break;

            }
            if(totalDeltaTime <= 0) {
                switch (to) {
                    case GROUND:
                        totalDeltaTime = 0;
                        game.mapOut(game.getGroundFloor().getMap());
                        game.getSpriteManager().setWorld(game.getGroundFloor().getWorld());
                        game.setScreen(game.getGroundFloor());
                        break;
                    case STREET:
                        totalDeltaTime = 0;
                        game.mapOut(game.getStreetView().getMap());
                        game.getSpriteManager().setWorld(game.getStreetView().getWorld());
                        game.setScreen(game.getStreetView());
                        break;
                }
            }
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeType.Filled);
        //Change the opacity of the block based on render
        shapeRenderer.setColor(0, 0, 0, totalDeltaTime);

        shapeRenderer.rect(0, 0, Con.WIDTH * 10, Con.HEIGHT * 10);
        // size
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
