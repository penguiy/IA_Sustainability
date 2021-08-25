package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Con;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;
import com.mygdx.game.Utils.WorldContactListener;

public class InfoScreen implements Screen {
    private Main myGame;

    private TmxMapLoader mapLoader; //helps load the map
    private TiledMap map; //the loaded map object
    private OrthogonalTiledMapRenderer renderer; //renders the map
    private OrthographicCamera camera;
    private Viewport viewport;

    private World world;
    private Stage info;
    private Table stats;

    private TextButton back;
    private ImageButton left;
    private Image page;
    private int pageCount =1;
    private ImageButton right;


    public Stage getStage(){
        return info;
    }

    public InfoScreen(final Main game){
        this.myGame = game;
        this.world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener(game));
        mapLoader = new TmxMapLoader(); //create an instance of built-in map loader object
        map = mapLoader.load(Con.STREET_VIEW_MAP); //using map loader object, load the tiled map that you made
        renderer = new OrthogonalTiledMapRenderer(map); //render the map.
        camera = new OrthographicCamera();
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, camera); //camera variable from before
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //set initial camera position
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.up =
                new TextureRegionDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Con.BUTTONG_BG))));

        back = new TextButton("Back to Title", style);
        back.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                myGame.setScreen(game.getTitleScreen());
                return false;
            }
        });
        info = new Stage(viewport);
        stats = new Table();
        TextureRegion leftArrow = new TextureRegion(new Texture(Con.ARROW_ICON));
        TextureRegion rightArrow = new TextureRegion(new Texture(Con.ARROW_ICON));
        rightArrow.flip(true,false);

        page = new Image(new Texture(pageCount + ".jpg"));
        left = new ImageButton(new TextureRegionDrawable(leftArrow));
        right = new ImageButton(new TextureRegionDrawable(rightArrow));
        left.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pageCount > 1) {
                    pageCount--;
                }
                return false;
            }
        });

        right.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(pageCount < 22){
                    pageCount++;
                }
                return false;
            }
        });

        stats.add(left);
        stats.add(page);
        stats.add(right);
        stats.row();
        stats.add();
        stats.add(back).height(20);
        stats.setFillParent(true);
        stats.debug();
        info.addActor(stats);
        Gdx.input.setInputProcessor(game.getInputListener());
    }
    @Override
    public void show() {

    }
    private void update(float dt)
    {
        camera.update();
        page = new Image(new Texture(pageCount + ".jpg"));

        stats.clear();
        stats.add(left);
        stats.add(page);
        stats.add(right);
        stats.row();
        stats.add();
        stats.add(back).height(20);
        stats.setFillParent(true);
        renderer.setView(camera); //sets the view from our camera so it would render only what our camera can see.
    }
    @Override
    public void render(float delta) {
        update(delta);
        if(myGame.getErrorLabel().getColor().a >0) {
            myGame.fadeError(delta);
        }
        //clear screen
        Gdx.gl.glClearColor(0, 1 , 1 ,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        myGame.getBatch().begin(); //open batch
        info.draw();
        myGame.getBatch().end(); //close the batch
        myGame.getBatch().setProjectionMatrix(camera.combined);

    }

    @Override
    public void resize(int width, int height) {
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

    }
}
