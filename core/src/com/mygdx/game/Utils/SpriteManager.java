package com.mygdx.game.Utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.Hud;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Sprites.SpriteBase;
import com.mygdx.game.Sprites.TempSprite;

import java.util.ArrayList;

//Contain all the sprites and tell them to move
public class SpriteManager{


    private ArrayList<SpriteBase> spriteList;

    private World world;
    private Main game;

    public SpriteManager(Main game, Hud hud){
        this.game = game;
        spriteList = new ArrayList<>();
    }
    public Tile[][] getLayout() {
        return layout;
    }

    private Tile[][] layout;
    //SpriteHandler should be the only thing changing the positions of the sprites




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
                    layout[y][x] = new Tile(true, x,y);
                }
                else{
                    System.out.print(" - ");
                    layout[y][x] = new Tile(false, currCell.getTile(),x,y);
                }
                if(colCount == 35){
                    System.out.print("\n");
                    colCount=0;
                }
            }
        }
    }
    public ArrayList<SpriteBase> getSpriteList() {
        return spriteList;
    }
    public void setWorld(World world) {
        this.world = world;
        spriteList.add(new TempSprite(world,game, new float[]{294,168}));
    }
}
