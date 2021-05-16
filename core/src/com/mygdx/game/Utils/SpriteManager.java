package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
import java.util.Random;

import javax.management.remote.rmi._RMIConnection_Stub;

//Contain all the sprites and tell them to move
public class SpriteManager{


    private ArrayList<SpriteBase> spriteList;

    private World world;
    private Main game;

    public Tile[][] getLayout() {
        return layout;
    }

    private Tile[][] layout;
    //SpriteHandler should be the only thing changing the positions of the sprites

    private boolean interval = false;


    public SpriteManager(Main game, Hud hud){
        this.game = game;
        spriteList = new ArrayList<>();
    }

    /**
     * Creates a 2D array of Cells from the TiledMap and stores it in layout
     * @param map
     */
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

    /**
     * Raises a flag, checks the odds of a self-resolution and adds points if needed every 10 in-game minutes
     */
    public void flagRaise(){
        if(!interval) {
            if (game.getHud().getMinutes() % 10 == 0) {
                //make sure this only runs once
                System.out.println("Flag HAPPENED");
                //Choose area
                Random random = new Random();
                int index = random.nextInt(5);
                //int selfResolve = game.getPlayer().getOdds().get(Con.TRIGGERS[index]);
                int selfResolve = 50;

                //Choose event, get odds
                int flag = random.nextInt(100);
                //if selfResolve fail
                System.out.println(flag);
                if (flag <= 100 - selfResolve) {
                    //Make bubble appear
                    System.out.println("Self Resolve Fail");
                } else {
                    //add points
                    System.out.println("Self Resolve Success");
                    game.getPlayer().setPoints(game.getPlayer().getPoints() + Con.BASE_POINTS);
                }
                interval = true;
            }
        }
        else if(game.getHud().getMinutes() % 10 != 0){
            interval = false;
        }
    }
}
