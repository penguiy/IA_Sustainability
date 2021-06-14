package com.mygdx.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;


public class Navi extends Sprite {
    private World world;
    private Main game;
    private Body body;
    private ScreenDisplay changeScreen;
    private TextureRegion region;

    public Navi(World world, float x, float y, Main game, ScreenDisplay screen){
        this.world = world;
        this.game = game;
        this.changeScreen = screen;
        setPosition(x,y);
        setBounds(getX(),getY(),Con.NAVI_WIDTH,Con.NAVI_HEIGHT);
        region = new TextureRegion(new Texture(Con.NAVI_SIDE_TEXTURE));
        setRegion(region);
        defineBody();
    }
    public Navi(World world, float x, float y, Main game, ScreenDisplay screen, boolean flipX, boolean flipY){
        this.world = world;
        this.game = game;
        this.changeScreen = screen;
        setPosition(x,y);
        setBounds(getX(),getY(),Con.NAVI_WIDTH,Con.NAVI_HEIGHT);
        region = new TextureRegion(new Texture(Con.NAVI_SIDE_TEXTURE));
        region.flip(flipX,flipY);
        setRegion(region);
        defineBody();
    }

    /**
     * Create the body and fixture for the Navi
     */
    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float)12.5,(float)12.5);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    /**
     * Update the position of the navi
     * @param dt delta time
     */
    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }

    /**
     * Call the change screen method in game
     */
    public void changeScreen(){
        game.changeScreen(changeScreen);
    }
}
