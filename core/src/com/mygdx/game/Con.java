package com.mygdx.game;

import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class Con {
    //Sizes
    public static final int WIDTH = 560; //Viewport width
    public static final int HEIGHT = 384; //Viewport height
    //Constants
    public static final int NAVI_WIDTH = 25;
    public static final int NAVI_HEIGHT = 25;
    public static final int STREET_NAVI_Y = 168;
    public static final int FINAL_HOUR = 16;
    public static final int FINAL_MIN = 0;
    public static final double STANDARD_TIME = 1.6;


    //File Paths/Strings
    public static final String GROUND_FLOOR_MAP = "iteration 1 - Ground floor.tmx";
    public static final String STREET_VIEW_MAP = "iteration 1 - Street view.tmx";
    public static final String DAY_ICON = "icons8-calendar-30.png";
    public static final String SETTINGS_ICON = "icons8-settings-48.png";
    public static final String TIME_ICON = "icons8-clock-24.png";

    //Temporary Data
    public static final String NAVI_SIDE_TEXTURE = "Navi Side-1.png.png";
    public static final String CAR_TEXTURE = "Car base-4.png.png";

    //Screen Data
    public static final HashMap<ScreenDisplay,String> STRING_TO_MAP_DICT = new HashMap<ScreenDisplay,String>(){{
        put(ScreenDisplay.GROUND, GROUND_FLOOR_MAP);
        put(ScreenDisplay.STREET, STREET_VIEW_MAP);
    }};
    public static final ArrayList<ScreenDisplay> ALL_SCREENS = new ArrayList<ScreenDisplay>(){{
        add(ScreenDisplay.GROUND);
        add(ScreenDisplay.STREET);
    }};

    //Mapping Out
    public static final String PASSABLE_STRING = "Passable";


    //Flag Things
    public static final String[] TRIGGERS = new String[]{"WATER","FOOD","LIGHT","AC","TRASH"};
    public static final ArrayList<ScreenDisplay> LOCATION = new ArrayList<ScreenDisplay>(){{
            add(ScreenDisplay.STREET);
            add(ScreenDisplay.GROUND);
    }};

    public static final HashMap<String,Integer> BASE_ODDS = new HashMap<String,Integer>(){{
        put("WATER", 0);
        put("FOOD", 0);
        put("LIGHT", 0);
        put("AC", 0);
        put("TRASH", 0);
    }};
    public static final HashMap<String, Double> BASE_MULTI = new HashMap<String,Double>(){{
        put("WATER", 1.0);
        put("FOOD", 1.0);
        put("LIGHT", 1.0);
        put("AC", 1.0);
        put("TRASH", 1.0);
    }};
    public static final HashMap<String,Integer> BASE_POINTS = new HashMap<String,Integer>(){{
        put("WATER", 10);
        put("FOOD", 10);
        put("LIGHT", 10);
        put("AC", 10);
        put("TRASH", 10);
    }};

    //GROUND FLOOR FLAG POSITIONS
    public static final ArrayList<int[]> GROUND_FLOOR_POSITIONS = new ArrayList<int[]>(){{
            add(new int[]{4, 9});//Class
            add(new int[]{4, 13});//Class
            add(new int[]{4, 28});//Bathroom
            add(new int[]{4, 32});//Bathroom

            add(new int[]{16, 9});//Cafeteria 1
            add(new int[]{18, 32});//Cafeteria 2
            add(new int[]{20, 25}); //Cafeteria
            add(new int[]{17, 17}); //Cafeteria

            add(new int[]{11, 6});//Hallway 1
            add(new int[]{11, 27});//Hallway 2
            add(new int[]{9, 20});//Hallway 3
    }};
    public static final ArrayList<int[]> STREET_POSITIONS = new ArrayList<int[]>(){{
            add(new int[]{3, 15});
            add(new int[]{20, 15});
            add(new int[]{5, 24});
            add(new int[]{5, 7});

            add(new int[]{3, 27});
            add(new int[]{15, 1});
            add(new int[]{2, 11});
            add(new int[]{16, 21});

            add(new int[]{6, 32});
            add(new int[]{20, 27});
            add(new int[]{19,11});
    }};
    public static final int FULL = 11;

    public static final String WATER_WASTE = "WATER";
    public static final String FOOD_WASTE = "FOOD";
    public static final String LIGHT_WASTE = "LIGHT";
    public static final String AC_WASTE = "AC";
    public static final String TRASH_WASTE = "TRASH";
    public static final double MULTI_GROWTH = 0.2;
    public static final int CLASS_PRICE = 50;
    public static final int INFRA_PRICE = 150;

}
