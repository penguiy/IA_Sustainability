//Class to handle the moving and changes of all sprites
package com.mygdx.game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.Hud;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;
import com.mygdx.game.Screens.Tile;
import com.mygdx.game.Sprites.Flag;
import com.mygdx.game.Sprites.TempSprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

//Contain all the sprites and tell them to move
public class SpriteManager{


    private ArrayList<TempSprite> peopleList;
    private ArrayList<int[]> taken;
    private ArrayList<int[]> notTaken;
    public void setFlagList(ArrayList<Flag> flagList) {
        this.flagList = flagList;
    }

    private ArrayList<Flag> flagList;


    private World world;
    private Main game;

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
        peopleList.add(new TempSprite(world, game, new float[]{294,168}));
    }

    /**
     * Raises a flag, checks the odds of a self-resolution and adds points if needed every 10 in-game minutes
     */
    public void flagRaise(){
        //make sure this only runs once
        if(!interval) {
            if (game.getHud().getMinutes() % 10 == 0) {
                //Choose area
                Random random = new Random();
                int index = random.nextInt(5);
                int selfResolve = game.getPlayer().getOdds().get(Con.TRIGGERS[index]);
                //Choose event, get odds
                int flag = random.nextInt(100);
                //if selfResolve fail
                if (flag <= 100 - selfResolve) {
                    //Change screen display later
                    taken = new ArrayList<>();
                    notTaken = new ArrayList<>();
                    int[] yx;
                    int ground = 0;
                    int street = 0;
                    for(Flag flagg : flagList) {
                        taken.add(flagg.getPosition());
                        if(flagg.getScreen().equals(ScreenDisplay.GROUND)){
                            ground++;
                        }else if(flagg.getScreen().equals(ScreenDisplay.STREET)){
                            street++;
                        }
                    }
                    ArrayList<ScreenDisplay> places = new ArrayList<ScreenDisplay>(){{
                        add(ScreenDisplay.GROUND);
                        add(ScreenDisplay.STREET);
                    }};
                    if(ground == Con.FULL){
                        places.remove(ScreenDisplay.GROUND);
                    }
                    if(street == Con.FULL){
                        places.remove(ScreenDisplay.STREET);
                    }
                    if(!places.isEmpty()) {//Make bubble appear
                        int location = random.nextInt(places.size());
                        for (Flag flagg : flagList) {
                            taken.add(flagg.getPosition());
                        }
                        switch (places.get(location)) {
                            case STREET:
                                for (int[] pos : Con.STREET_POSITIONS) {
                                    if (!taken.contains(pos)) {
                                        notTaken.add(pos);
                                    }
                                }
                                if (!notTaken.isEmpty()){
                                    yx = notTaken.get(random.nextInt(notTaken.size()));
                                    flagList.add(new Flag(game.getStreetView().getWorld(), yx, game, Con.TRIGGERS[index],
                                            ScreenDisplay.STREET));
                                }
                                break;
                            case GROUND:
                                for (int[] pos : Con.GROUND_FLOOR_POSITIONS) {
                                    if (!taken.contains(pos)) {
                                        notTaken.add(pos);
                                    }
                                }
                                if (!notTaken.isEmpty()){
                                    yx = notTaken.get(random.nextInt(notTaken.size()));
                                    flagList.add(new Flag(game.getGroundFloor().getWorld(), yx, game, Con.TRIGGERS[index],
                                            ScreenDisplay.GROUND));
                                }
                                break;
                        }
                    }
                } else {
                    //add points
                    game.getPlayer().addPoints((int)(Con.BASE_POINTS.get(Con.TRIGGERS[index]) * game.getPlayer().getMulti().get(Con.TRIGGERS[index])));
                }
                interval = true;
            }
        }
        else if(game.getHud().getMinutes() % 10 != 0){
            interval = false;
        }
    }

    /**
     * set boolean "clicked" of all flags in flagList to true
     */
    public void wipe(){
        for(Flag flag : flagList){
            flag.setClicked(true);
        }
    }
}
