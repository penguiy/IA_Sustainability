package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Sprites.Navi;
import com.mygdx.game.Sprites.TempSprite;

import java.util.ArrayList;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture objectA = contact.getFixtureA();
        Fixture objectB = contact.getFixtureB();

//Generalise this into 1 method using inputproccessor
        if(objectA.getUserData() instanceof Navi){
            Navi navi = (Navi) objectA.getUserData();
            navi.changeScreen();
        }
        else if(objectB.getUserData() instanceof StreetView && objectA.getUserData() instanceof TempSprite){
            System.out.println("detected");
        }
        else if(objectA.getUserData() instanceof Flag){
            Flag flag = (Flag) objectA.getUserData();
            flag.addPoints();
        }
        /*
        Else if( click a flag, gain points);
         */
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
