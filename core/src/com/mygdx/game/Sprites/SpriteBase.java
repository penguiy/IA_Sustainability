package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Screens.Tile;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class SpriteBase extends Sprite {
    //Schedule - At XX:XX time on HUD
    // should probably add days as well
    private HashMap<int[],int[]> schedule;
    //onScreen - boolean of whether sprite is on screen to be drawn
    private boolean onScreen;
    //Position - Where the sprite actually is
    private float[] position;

    public HashMap<int[], int[]> getSchedule() {
        return schedule;
    }

    public SpriteBase(){
        schedule = new HashMap<>();
    }

    //Calculate distance to point
    public ArrayList<Tile> calcSteps(int x, int y){
        return null;
    }

    public void moveAround(float[] bounds) {
    }

    public void move(int day, int hour, int sec) {
    }

    public void update(float delta){
    }

    public void locatePosition(){
        position = new float[]{getX(),getY()};
    }

    public boolean isOnScreen() {
        return onScreen;
    }
}
