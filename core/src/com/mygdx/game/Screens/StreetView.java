//Street level screen class
package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Sprites.Navi;
import com.mygdx.game.Sprites.TempSprite;
import com.mygdx.game.Utils.WorldContactListener;

public class StreetView implements Screen {

    private Main myGame;

    private TmxMapLoader mapLoader; //helps load the map
    private TiledMap map; //the loaded map object
    private OrthogonalTiledMapRenderer renderer; //renders the map
    private OrthographicCamera camera;
    private Viewport viewport;

    private Hud hud;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Vector3 touchPos;
    private Body clickBody;

    private Navi schoolNavi;

    private Car car;
    private Car car2;
    public StreetView(Main game) {
        this.myGame = game;
        this.hud = game.getHud();
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
        schoolNavi = new Navi(world, Con.WIDTH-145,Con.STREET_NAVI_Y, myGame, ScreenDisplay.GROUND,true,false);

        //Temps
        car = new Car(world, 115,0-(36+18), myGame);
        car2 = new Car(world, 115,0-(150+18), myGame);
        Gdx.input.setInputProcessor(game.getInputListener());
    }

    private void update(float dt) {
        world.step(1/60f, 6, 2);
        //If a clickFixture exists, destroy the body
        if(clickBody != null) {
            world.destroyBody(clickBody);
            clickBody = null;
        }
        //Update positions of all the sprites
        for (TempSprite sprite: myGame.getSpriteManager().getSpriteList()) {
            sprite.update(dt);
        }
        //Update positions of all the flags
        for (Flag flag: myGame.getSpriteManager().getFlagList()) {
            if(flag.isBodyDefined()) {
                flag.update();
            }
            if(flag.isClicked()){
                //If the flag is clicked destroy that flag
                world.destroyBody(flag.getBody());
            }
        }
        car.dropOff(dt);
        car2.dropOff(dt);
        for(int i = 0; i < myGame.getSpriteManager().getFlagList().size(); i++) {
            if(myGame.getSpriteManager().getFlagList().get(i).isClicked()){
                myGame.getSpriteManager().getFlagList().remove(i);
                i--;
            }
        }
        hud.update(dt);
        schoolNavi.update(dt);
        camera.update();
        renderer.setView(camera); //sets the view from our camera so it would render only what our camera can see.
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(!(myGame.getDisplaying().equals(ScreenDisplay.PAUSE)||myGame.getDisplaying().equals(ScreenDisplay.DAYEND))) {
            update(delta);
        }
        //clear screen
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //Open Batch
        myGame.getBatch().begin();
        //Draw Cars
        car.draw(myGame.getBatch());
        car2.draw(myGame.getBatch());
        //Draw every Person that is in StreetView
        for (TempSprite sprite: myGame.getSpriteManager().getSpriteList()) {
            sprite.draw(myGame.getBatch());
        }
        //Draw every Flag in StreetView
        for (Flag flag: myGame.getSpriteManager().getFlagList()) {
            if(!flag.isClicked() && flag.getScreen() == ScreenDisplay.STREET) {
                flag.draw(myGame.getBatch());
            }
        }
        //Draw the Navi
        schoolNavi.draw(myGame.getBatch());
        //End batch
        myGame.getBatch().end();
        hud.getStage().draw();
        myGame.getBatch().setProjectionMatrix(camera.combined); //updates our batch with our Camera's view and projection matrices.
        box2DDebugRenderer.render(world,camera.combined);
    }

    /**
     * Creates a temporary fixture at mouse click position
     */
    public void clickFixture(int x, int y){
        BodyDef bodyDef = new BodyDef();
        touchPos = new Vector3(x, y, 0);
        viewport.unproject(touchPos);
        System.out.println("("+touchPos.x+","+touchPos.y+")");
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

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
}
