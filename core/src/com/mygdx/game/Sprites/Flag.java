//Class that raises the event flags that grant points
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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


    public boolean isBodyDefined() {
        return definedBody;
    }

    public void setDefinedBody(boolean definedBody) {
        this.definedBody = definedBody;
    }

    public int[] getPosition() {
        return position;
    }

    private int[] position;

    public ScreenDisplay getScreen() {
        return onScreen;
    }

    //USE THIS TO DETERMINE WHICH BODIES TO RENDER on which screen
    private ScreenDisplay onScreen;

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    private boolean clicked;


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
        onScreen = screen;
        this.position = yx;
        setPosition(yx[1]*16,yx[0]*16);
        setBounds(getX(),getY(), Con.NAVI_WIDTH,Con.NAVI_HEIGHT);
        /*switch (type){
            case Con.AC_WASTE:
                region =
                break;
            case Con.FOOD_WASTE:
                region =
                break;
            case Con.LIGHT_WASTE:
                region =
                break;
            case Con.TRASH_WASTE:
                region =
                break;
            case Con.WATER_WASTE:
                region =
                break;
        }*/
        //temp
        region = new TextureRegion(new Texture(Con.NAVI_SIDE_TEXTURE));
        setRegion(region);
        defineBody();
        //Find a way to wait until on correct screen to define body
    }

    /**
     * Create the body and fixture for the Flag
     */
    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float)12.5,(float)12.5);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
        update();
    }

    public void update(){
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }

    /**
     * Add points to the Player object based on the multiplier
     */
    public void addPoints(){
        game.getPlayer().addPoints((int)(Con.BASE_POINTS.get(type) * game.getPlayer().getMulti().get(type)));
    }


}
