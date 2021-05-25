package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private Vector3 touchPos;
    private Body clickBody;

    public TitleScreen(Main game) {
        this.myGame = game;
        this.world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener(game));
        box2DDebugRenderer = new Box2DDebugRenderer();

        mapLoader = new TmxMapLoader(); //create an instance of built-in map loader object
        map = mapLoader.load(Con.STREET_VIEW_MAP); //using map loader object, load the tiled map that you made
        renderer = new OrthogonalTiledMapRenderer(map); //render the map.
        camera = new OrthographicCamera();
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //set initial camera position


        touchPos = new Vector3();

        Gdx.input.setInputProcessor(game.getInputListener());
    }
    public void clickFixture(int x, int y){
        BodyDef bodyDef = new BodyDef();
        touchPos = new Vector3(x, y, 0);
        viewport.unproject(touchPos);
        bodyDef.position.set(touchPos.x,touchPos.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        clickBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape click = new CircleShape();
        click.setRadius(4);
        fixtureDef.shape = click;
        clickBody.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
