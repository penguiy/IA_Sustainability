//Class that raises the event flags that grant points
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Con;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;

public class Flag extends Sprite {

    private final World world;
    private final Main game;
    private Body body;
    private TextureRegion region;
    private final String type;
    private boolean definedBody = false;
    private Animation<TextureRegion> anim;
    private float animTimer;
    private int[] position;
    private ScreenDisplay onScreen;//USE THIS TO DETERMINE WHICH BODIES TO RENDER on which screen
    private boolean clicked;

    public boolean isClicked() {
        return clicked;
    }

    public boolean isBodyDefined() {
        return definedBody;
    }

    public void setDefinedBody(boolean definedBody) {
        this.definedBody = definedBody;
    }

    public int[] getPosition() {
        return position;
    }

    public ScreenDisplay getScreen() {
        return onScreen;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public Body getBody() {
        return body;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public String getType() {
        return type;
    }

    public Flag(World world, int[] yx, Main game, String type, ScreenDisplay screen){
        this.world = world;
        this.game = game;
        this.type = type;
        this.onScreen = screen;
        this.position = yx;
        this.animTimer = 0;
        setPosition(yx[1]*16,yx[0]*16);
        setBounds(getX(),getY(), Con.NAVI_WIDTH,Con.NAVI_HEIGHT);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        int animEnd = 0;
        String regioni = "";
        frames.clear();
        if (Con.TRIGGERS[0].equals(type)) {
            region = new TextureRegion(this.game.getAtlas().findRegion(Con.WATER_STRING), 0, 0, 25, 25);
            regioni = Con.WATER_STRING;
            animEnd = Con.WATER_ANIMEND;
        } else if (Con.TRIGGERS[1].equals(type)) {
            region = new TextureRegion(this.game.getAtlas().findRegion(Con.FOOD_STRING), 0, 0, 25, 25);
            regioni = Con.FOOD_STRING;
            animEnd = Con.FOOD_ANIMEND;
        } else if (Con.TRIGGERS[2].equals(type)) {
            region = new TextureRegion(this.game.getAtlas().findRegion(Con.LIGHT_STRING), 0, 0, 25, 25);
            regioni = Con.LIGHT_STRING;
            animEnd = Con.LIGHT_ANIMEND;
        } else if (Con.TRIGGERS[3].equals(type)) {
            region = new TextureRegion(this.game.getAtlas().findRegion(Con.AC_STRING), 0, 0, 25, 25);
            regioni = Con.AC_STRING;
            animEnd = Con.AC_ANIMEND;
        } else if (Con.TRIGGERS[4].equals(type)) {
            region = new TextureRegion(this.game.getAtlas().findRegion(Con.TRASH_STRING), 0, 0, 25, 25);
            regioni = Con.TRASH_STRING;
            animEnd = Con.TRASH_ANIMEND;

        }
        for(int i = 0; i < animEnd; i++)
        {
            frames.add(new TextureRegion(game.getAtlas().findRegion(regioni), i * 25, 0, 25, 25));
        }
        anim = new Animation<>(0.1f, frames);
        setRegion(region);
        defineBody();
    }

    /**
     * Change the frame of the flag animation if timer has been reached
     * @param dt delta time
     */
    public void changeFrame(float dt){
        region = anim.getKeyFrame(animTimer,true);
        animTimer += dt;
        setRegion(region);
    }
    /**
     * Create the body and fixture for the Flag
     */
    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float)12.5,(float)12.5);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }

    /**
     * update position and texture of the flag
     * @param dt delta time
     */
    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
        changeFrame(dt);
    }

    /**
     * Add points to the Player object based on the multiplier
     */
    public void addPoints(){
        game.getPlayer().addPoints((int)(Con.BASE_POINTS.get(type) * game.getPlayer().getMulti().get(type)));
    }
}
