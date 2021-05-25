//Class that will be stored to the database and used for loading saves
package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int points;



    private int  dayNum;
    private HashMap<String, Integer> odds;
    private HashMap<String, Integer> multiplier;
    private ArrayList<String> purchases;

    public Player(int points, HashMap<String, Integer> odds, HashMap<String, Integer> multiplier, int dayNum) {
        this.points = points;
        this.odds = odds;
        this.multiplier = multiplier;
        this.dayNum = dayNum;
    }

    public Player() {
        this.points = 0;
        this.odds = Con.BASE_ODDS;
        this.multiplier = Con.BASE_MULTI;
        this.dayNum = 1;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Add points to the count
     * @param points : Amount of points to be added
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Subtract points from the count
     * @param points : Amount of points to be subtracted
     */
    public void subPoints(int points){
        this.points -= points;
    }

    public HashMap<String, Integer> getOdds() {
        return odds;
    }

    /**
     * Edit the odds of a self-resolve flag for a given type of event
     * @param section : Event that causes the flag (Water, Electricity, Food etc.)
     * @param newOdds : Percentage out of 100 of a self-resolution
     */
    public void setOdds(String section, int newOdds) {
        this.odds.put(section,newOdds);
    }
    public HashMap<String, Integer> getMulti() {
        return multiplier;
    }

    /**
     * Edit the points multiplier for a given type of event
     * @param section : Event that causes the flag (Water, Electricity, Food etc.)
     * @param newMulti : Multiplier value
     */
    public void setMultiplier(String section, int newMulti) {
        this.odds.put(section,newMulti);
    }

    public int getDayNum() {
        return dayNum;
    }

    public void nextDay() {
        this.dayNum = dayNum++;
    }
}
