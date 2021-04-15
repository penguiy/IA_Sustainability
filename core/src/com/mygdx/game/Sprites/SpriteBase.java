package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;


public abstract class SpriteBase extends Sprite {
    //Schedule - At XX:XX time on HUD

    //onScreen - boolean of whether sprite is on screen to be drawn
    private boolean onScreen;
    //Position - Where the sprite actually is
    private float[] position;
    public SpriteBase(){
    }

    //Calculate distance to point
    public int calcSteps(){
        //openSet
        //closedSet
        return 0;
    }

    public void moveAround(float[] bounds){

    }
}
