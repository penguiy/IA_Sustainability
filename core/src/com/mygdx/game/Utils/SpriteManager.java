package com.mygdx.game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.Hud;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Sprites.TempSprite;

import java.util.ArrayList;
import java.util.Random;

//Contain all the sprites and tell them to move
public class SpriteManager{


    private ArrayList<TempSprite> peopleList;

    public void setFlagList(ArrayList<Flag> flagList) {
        this.flagList = flagList;
    }

    private ArrayList<Flag> flagList;


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
        peopleList = new ArrayList<>();
        flagList = new ArrayList<>();
    }



    public ArrayList<TempSprite> getSpriteList() {
        return peopleList;
    }

    public ArrayList<Flag> getFlagList() {
        return flagList;
    }

    public void setWorld(World world) {
        this.world = world;
        //FIGURE OUT WHAT THE HELL THE POS[] DOES
        //peopleList.add(new TempSprite(world, game, new float[]{294,168}));
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
                int selfResolve = game.getPlayer().getOdds().get(Con.TRIGGERS[index]);
                //Choose event, get odds
                int flag = random.nextInt(100);
                //if selfResolve fail
                System.out.println(flag);
                if (flag <= 100 - selfResolve) {
                    //Make bubble appear
                    flagList.add(new Flag(world,0,0,game,Con.TRIGGERS[index]));
                    System.out.println("Self Resolve Fail");
                } else {
                    //add points
                    System.out.println("Self Resolve Success");
                }
                interval = true;
            }
        }
        else if(game.getHud().getMinutes() % 10 != 0){
            interval = false;
        }
    }

    /**
     * Give location of flag
     * @param x x coordinate in mapped out array
     * @param y y coordinate in mapped out array
     */
    public int[] FlagLocation(int x,int y){
        //TODO use this to place flags in distinct locations
        return new int[]{x * 16 - 8, (Con.HEIGHT - y) * 16 - 8};
    }
}
