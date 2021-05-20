//Class that will be stored to the database and used for loading saves
package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int points;
    private HashMap<String, Integer> odds;
    private HashMap<String, Integer> multiplier;
    private ArrayList<String> purchases;

    public Player(int points, HashMap<String, Integer> odds, HashMap<String, Integer> multiplier) {
        this.points = points;
        this.odds = odds;
        this.multiplier = multiplier;
    }

    public Player() {
        this.points = 0;
        this.odds = Con.BASE_ODDS;
        this.multiplier = Con.BASE_MULTI;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }
    public void subPoints(int points){
        this.points -= points;
    }

    public HashMap<String, Integer> getOdds() {
        return odds;
    }

    public void setOdds(String section, int newOdds) {
        this.odds.put(section,newOdds);
    }
    public HashMap<String, Integer> getMulti() {
        return multiplier;
    }

    public void setMultiplier(String section, int newMulti) {
        this.odds.put(section,newMulti);
    }
}
