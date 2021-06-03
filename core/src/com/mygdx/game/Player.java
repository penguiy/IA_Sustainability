//Class that will be stored to the database and used for loading saves
package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int balance;
    private int  dayNum;
    private HashMap<String, Double> multiplier;
    private HashMap<String, Integer> odds;
    private HashMap<String, Integer> classCount;
    private HashMap<String, Integer> infraCount;
    private ArrayList<String> infraPurchase;

    private int infraPrice;
    private int classPrice;

    public Player(int points, int dayNum, HashMap<String, Integer> odds, HashMap<String, Double> multiplier, HashMap<String, Integer> classCount, HashMap<String, Integer> infraCount, ArrayList<String> infraPurchase) {
        this.balance = points;
        this.dayNum = dayNum;
        this.odds = odds;
        this.multiplier = multiplier;
        this.classCount = classCount;
        this.infraCount = infraCount;
        this.infraPurchase = infraPurchase;
        this.infraPrice = Con.INFRA_PRICE;
        this.classPrice = Con.CLASS_PRICE;
    }

    public Player() {
        this.balance = 0;
        this.odds = Con.BASE_ODDS;
        this.multiplier = Con.BASE_MULTI;
        this.classCount = new HashMap<String, Integer>(){{
            put("WATER", 0);
            put("FOOD", 0);
            put("LIGHT", 0);
            put("AC", 0);
            put("TRASH", 0);
        }};
        this.infraCount = new HashMap<String, Integer>(){{
            put("WATER", 0);
            put("FOOD", 0);
            put("LIGHT", 0);
            put("AC", 0);
            put("TRASH", 0);
        }};
        this.infraPurchase = new ArrayList<String>();
        this.infraPrice = Con.INFRA_PRICE;
        this.classPrice = Con.CLASS_PRICE;
        this.dayNum = 1;
    }

    public int getPoints() {
        return balance;
    }

    /**
     * Add points to the count
     * @param points : Amount of points to be added
     */
    public void addPoints(int points) {
        this.balance += points;
    }

    /**
     * Subtract points from the count
     * @param points : Amount of points to be subtracted
     */
    public void subPoints(int points){
        this.balance -= points;
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
        this.odds.put(section,this.odds.get(section)+newOdds);
    }
    public HashMap<String, Double> getMulti() {
        return multiplier;
    }

    /**
     * Edit the points multiplier for a given type of event
     * @param section : Event that causes the flag (Water, Electricity, Food etc.)
     * @param newMulti : Multiplier value
     */
    public void setMultiplier(String section, double newMulti) {
        this.multiplier.put(section,this.multiplier.get(section)+newMulti);
    }

    public int getDayNum() {
        return dayNum;
    }

    /**
     * increases dayNum by 1
     */
    public void nextDay() {
        this.dayNum = dayNum+1;
    }

    public HashMap<String, Integer> getInfraCount() {
        return infraCount;
    }
    public int getInfraCount(String str) {
        return infraCount.get(str);
    }

    public HashMap<String, Integer> getClassCount() {
        return classCount;
    }
    public int getClassCount(String str) {
        return classCount.get(str);
    }

    public ArrayList<String> getInfraPurchase() {
        return infraPurchase;
    }
    /**
     * calculates and return the final price of the odds increase for infrastructure
     *
     * @param field : key for infraCount
     * @return price of Multiplier after factoring in purchase count.
     */
    public int getInfraPrice(String field) {
        if(infraCount.get(field) == 0){
            return infraPrice;
        }
        return (infraCount.get(field)* infraCount.get(field) * infraPrice);
    }

    /**
     * calculates and return the final price of the multiplier for classes
     *
     * @param field : key for classCount
     * @return price of Multiplier after factoring in purchase count.
     */
    public int getClassPrice(String field) {
        if(classCount.get(field) == 0){
            return classPrice;
        }
        return (classCount.get(field) * classPrice);
    }

}
