package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Main;

public class GroundFloor implements Screen {

    private Main myGame;
    private Texture stair = new Texture("Stairs.png");


    private TmxMapLoader mapLoader; //helps load the map
    private TiledMap map; //the loaded map object
    private OrthogonalTiledMapRenderer renderer; //renders the map

    public GroundFloor(Main game){
        this.myGame = game;
        mapLoader = new TmxMapLoader(); //create an instance of built-in map loader object
        map = mapLoader.load("levelOne.tmx"); //using map loader object, load the tiled map that you made
        renderer = new OrthogonalTiledMapRenderer(map); //render the map.
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        myGame.getBatch().begin();
        myGame.getBatch().draw(stair,0,0);
        myGame.getBatch().end();
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

    }
}
