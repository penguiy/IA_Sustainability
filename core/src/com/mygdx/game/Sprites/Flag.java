package com.mygdx.game.Sprites;

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


    private World world;
    private Main game;
    private Body body;
    private TextureRegion region;
    private String type;

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    private boolean clicked;

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    private boolean trash;

    public Body getBody() {
        return body;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public String getType() {
        return type;
    }

    public Flag(World world, float x, float y, Main game, String type){
        this.world = world;
        this.game = game;
        this.type = type;
        setPosition(x,y);
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
    }

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
    }

    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }

    public void addPoints(){
        game.getPlayer().addPoints(Con.BASE_POINTS.get(type) * game.getPlayer().getMulti().get(type));
    }



}
