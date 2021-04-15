package com.mygdx.game.Screens;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class Tile {
    private float g;
    private float h;
    private float f;
    private boolean obstacle;
    private float[] Xbounds;
    private float[] Ybounds;
    private TiledMapTile tile;

    public Tile(boolean obstacle){
        this.obstacle = obstacle;
        this.tile = null;
    }
    public Tile(boolean obstacle, TiledMapTile tile){
        this.obstacle = obstacle;
        this.tile = tile;
    }
}
