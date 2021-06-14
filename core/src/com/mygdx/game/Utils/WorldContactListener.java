package com.mygdx.game.Utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Main;
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Sprites.Navi;

public class WorldContactListener implements ContactListener {
    public Main game;

    public WorldContactListener(Main game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture objectA = contact.getFixtureA();
        Fixture objectB = contact.getFixtureB();

        if(objectA.getUserData() instanceof Navi){
            Navi navi = (Navi) objectA.getUserData();
            navi.changeScreen();
        }
        else if(objectA.getUserData() instanceof Flag){
            Flag flag = (Flag) objectA.getUserData();
            flag.addPoints();
            flag.setClicked(true);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
