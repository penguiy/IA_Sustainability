package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Screens.Tile;

import java.util.ArrayList;


public abstract class SpriteBase extends Sprite {
    //Schedule - At XX:XX time on HUD

    //onScreen - boolean of whether sprite is on screen to be drawn
    private boolean onScreen;
    //Position - Where the sprite actually is
    private float[] position;
    public SpriteBase(){
    }

    //Calculate distance to point
    public ArrayList<Tile> calcSteps(int x, int y){
        //openSet
        //closedSet
        return null;
    }

    public void moveAround(float[] bounds){

    }
    public void update(float delta){
    }
}
