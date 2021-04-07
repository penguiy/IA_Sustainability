package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Con;
import com.mygdx.game.Main;

public class GroundFloor implements Screen {

    private Main myGame;
    private Texture stair = new Texture("Stairs.png");


    private TmxMapLoader mapLoader; //helps load the map
    private TiledMap map; //the loaded map object
    private OrthogonalTiledMapRenderer renderer; //renders the map
    private OrthographicCamera camera;
    private Viewport viewport;

    public GroundFloor(Main game){
        this.myGame = game;
        mapLoader = new TmxMapLoader(); //create an instance of built-in map loader object
        map = mapLoader.load(Con.GROUND_FLOOR_MAP); //using map loader object, load the tiled map that you made
        renderer = new OrthogonalTiledMapRenderer(map); //render the map.
        camera = new OrthographicCamera();
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //set initial camera position
    }
    private void update(float dt)
    {
        camera.update();
        renderer.setView(camera); //sets the view from our camera so it would render only what our camera can see.
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //clear screen
        Gdx.gl.glClearColor(0, 1, 1 ,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        myGame.getBatch().begin();

        myGame.getBatch().end();
        myGame.getBatch().setProjectionMatrix(camera.combined); //updates our batch with our Camera's view and projection matrices.

    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height); //viewport gets adjusted accordingly
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
        map.dispose();
        renderer.dispose();
    }
}
