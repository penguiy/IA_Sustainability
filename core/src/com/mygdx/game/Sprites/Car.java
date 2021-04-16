package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;

public class Car extends Sprite {
    private World world;
    private Main game;

    private Body body;

    private TextureRegion region;

    private boolean turning, anim;



    public Car(World world, float x, float y, Main game){
        this.world = world;
        this.game = game;
        setPosition(x,y);
        setBounds(getX(),getY(), 39,54);
        region = new TextureRegion(new Texture(Con.CAR_TEXTURE));
        setRegion(region);
        defineBody();
    }
    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
    }

    //TODO:Use similar idea to code different sequences, Use when reach "X" position change what im rendering
    public void dropOff(float delta) {
        if(true/* If no other car is in anim */) {
            if (getY() <= 220/*110*/ && !turning) {
                this.setPosition(getX(), getY() + 0.7f);
                anim = true;
            }
            else if (getX() > 47) {
                turning = true;
                this.rotate(0.5f);
                //System.out.println(getX());
                if (getX() >= 96.7) {
                    this.setPosition(getX() - delta - 0.1f, getY() + 0.33f + delta / 2);
                } else if (getX() > 96.7) {
                    this.setPosition(getX() - 0.07f, getY() + 0.05f);
                } else if (getX() > 96.4) {
                    this.setPosition(getX() - 0.07f, getY() + 0.07f);
                } else {
                    this.setPosition(getX() - 0.25f, getY() - 0.03f + delta);
                }
            } else {
                this.setPosition(getX(), getY() - 0.7f);
            }
        }
    }

    public Body getBody() {
        return body;
    }
}
