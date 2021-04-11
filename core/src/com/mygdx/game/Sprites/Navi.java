package com.mygdx.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.ScreenDisplay;


public class Navi extends Sprite {
    private World world;
    private Body body;
    private ScreenDisplay changeScreen;

    public Navi(World world, float x, float y){
        this.world = world;
        setPosition(x,y);
        defineBody();
    }

    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Con.NAVI_WIDTH, Con.NAVI_HEIGHT);
        fixtureDef.shape = shape;
    }
}
