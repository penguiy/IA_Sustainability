package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Screens.Tile;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class SpriteBase extends Sprite {


    //onScreen - boolean of whether sprite is on screen to be drawn
    private boolean onScreen;
    //Position - Where the sprite actually is


    public SpriteBase(){

    }

    //Calculate distance to point
    public ArrayList<Tile> calcSteps(int x, int y){
        return null;
    }

    public void moveAround(float[] bounds) {
    }

    public void move(float delta, int day, int hour, int sec) {
    }

    public void update(float delta){
    }


    public boolean isOnScreen() {
        return onScreen;
    }

    public void setOnScreen(boolean onScreen) {
        this.onScreen = onScreen;
    }
}
