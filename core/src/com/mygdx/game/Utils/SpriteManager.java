package com.mygdx.game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Con;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Sprites.SpriteBase;

import java.util.ArrayList;

public class SpriteManager {
    ArrayList<SpriteBase> spriteList;

    public SpriteManager(){

    }
    public Tile[][] getLayout() {
        return layout;
    }

    private Tile[][] layout;
    //SpriteHandler should be the only thing changing the positions of the sprites

    public void update(float delta){

    }

    public void mapOut(TiledMap map){
        this.layout = new Tile[24][35];
        TiledMapTileLayer passable = (TiledMapTileLayer)map.getLayers().get(Con.PASSABLE_STRING);
        int colCount = 0;
        for(int y = 0; y < 24; y++){
            for (int x = 0; x < 35; x++){
                TiledMapTileLayer.Cell currCell = passable.getCell(x,23-y);
                colCount++;
                if(currCell == null) {
                    System.out.print(" X ");
                    layout[23-y][x] = new Tile(true);
                }
                else{
                    System.out.print(" - ");
                    layout[23-y][x] = new Tile(false);
                }
                if(colCount == 35){
                    System.out.print("\n");
                    colCount=0;
                }
            }
        }
    }


}
