package com.mygdx.game.Screens;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

//Tiles in the map for when mapping it out to path
public class Tile {
    private float gCost;
    private float heuristic;
    private float function;


    private boolean obstacle;
    private int x;
    private int y;

    private TiledMapTile tile;
    private Tile parent;



    public Tile(boolean obstacle, int x, int y){
        this.obstacle = obstacle;
        this.tile = null;
        this.parent = null;
        this.x = x;
        this.y = y;
        this.gCost = 0;
        this.heuristic = 0;
    }
    public Tile(boolean obstacle, TiledMapTile tile, int x, int y){
        this.obstacle = obstacle;
        this.tile = tile;
        this.parent = null;
        this.x = x;
        this.y = y;
    }

    public float getG() {
        return gCost;
    }

    public float getH() {
        return heuristic;
    }

    public float getF() {
        return function;
    }

    public boolean equals(Tile tile){
        if(this.x == tile.x && this.y == tile.y){
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setG(float g) {
        this.gCost = g;
    }

    public void setH(float h) {
        this.heuristic = h;
    }

    public void setF(float f) {
        this.function = f;
    }

    public Tile getParent() {
        return parent;
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    }

    public TiledMapTile getTile() {
        return tile;
    }
    public boolean isObstacle() {
        return obstacle;
    }
    public String toString(){
        return "<" + x+","+y+">";
    }

}
