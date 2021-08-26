package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Con;
import com.mygdx.game.Hud;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;
import com.mygdx.game.Sprites.Car;
import com.mygdx.game.Sprites.Navi;
import com.mygdx.game.Utils.WorldContactListener;

public class TitleScreen implements Screen {

    private Main myGame;

    private TmxMapLoader mapLoader; //helps load the map
    private TiledMap map; //the loaded map object
    private OrthogonalTiledMapRenderer renderer; //renders the map
    private OrthographicCamera camera;
    private Viewport viewport;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public Stage getStage() {
        return selectMenu;
    }

    private Stage selectMenu;
    private Table menuTable;


    private TextButton start;
    private TextButton delete;
    private TextButton more;
    

    public TitleScreen(final Main game) {
        this.myGame = game;
        this.world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener(game));
        mapLoader = new TmxMapLoader(); //create an instance of built-in map loader object
        map = mapLoader.load(Con.STREET_VIEW_MAP); //using map loader object, load the tiled map that you made
        renderer = new OrthogonalTiledMapRenderer(map); //render the map.
        camera = new OrthographicCamera();
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, camera); //camera variable from before
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //set initial camera position

        selectMenu = new Stage(viewport);
        menuTable = new Table();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.up = new TextureRegionDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Con.BUTTONG_BG))));
        //MAKE A BUTTON THAT GOES FROM NEW TO CONTINUE DEPENDING IF THERES A SAVE
        if(myGame.getPref().getBoolean(Con.FILE_EXISTS)){
            start = new TextButton("LOAD SAVE", style);
        } else {
            start = new TextButton("START NEW", style);
        }
        start.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                myGame.changeScreen(ScreenDisplay.STREET);
                delete.setTouchable(Touchable.disabled);
                return false;
            }
        });

        //DELETE SAVE BUTTON
        delete = new TextButton("DELETE SAVE", style);
        delete.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(game.getPref().getBoolean(Con.FILE_EXISTS)){
                    myGame.deleteSave();
                }
                else{
                    myGame.showError();
                }
                return false;
            }
        });
        more = new TextButton("MORE INFO", style);
        more.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                myGame.setScreen(game.getInfoScreen());
                return false;
            }
        });

        menuTable.add(start).size(150,30).padTop(50);
        menuTable.row();
        menuTable.add(delete).size(150,30).padTop(50).row();
        menuTable.center();
        menuTable.add(more).size(150,30).padTop(50);
        menuTable.center();
        menuTable.setFillParent(true);

        menuTable.debug();
        selectMenu.addActor(menuTable);
        Gdx.input.setInputProcessor(game.getInputListener());
    }

    private void update(float dt)
    {
        camera.update();
        if(myGame.getPref().getBoolean(Con.FILE_EXISTS)){
            start.setText("CONTINUE");
        } else {
            start.setText("START");
        }
        renderer.setView(camera); //sets the view from our camera so it would render only what our camera can see.
    }
    @Override
    public void show() {

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
        selectMenu.draw();
        myGame.getBatch().end(); //close the batch
        myGame.getBatch().setProjectionMatrix(camera.combined); //updates our batch with our Camera's view and projection matrices.
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
        renderer.dispose();
    }
}
