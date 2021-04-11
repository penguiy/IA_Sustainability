package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Sprites.Navi;

import java.util.ArrayList;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture objectA = contact.getFixtureA();
        Fixture objectB = contact.getFixtureB();
        if(objectB.getUserData() instanceof GroundFloor && objectA.getUserData() instanceof Navi){
            GroundFloor screen = (GroundFloor) objectB.getUserData();
            Navi navi = (Navi) objectA.getUserData();
            navi.changeScreen();
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