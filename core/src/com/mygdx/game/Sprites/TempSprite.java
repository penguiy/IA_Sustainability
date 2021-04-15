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
import com.mygdx.game.Utils.SpriteManager;

public class TempSprite extends SpriteBase {
    private World world;
    private Main game;

    private Body body;

    private TextureRegion region;


    public TempSprite(World world, Main game, float[] pos){
        this.world = world;
        this.game = game;
        setPosition(pos[0],pos[1]);
        setBounds(getX(),getY(),16,16);

        region = new TextureRegion(new Texture("Stairs.png"));

    }

    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getRegionWidth(),getRegionY());
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }
    public void update(float dt){
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2);
    }

    @Override
    public int calcSteps() {
        //A* algorithm
        return 0;
    }
}
