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

    public static final int TILEMAP_WIDTH = 35;
    public static final int TILEMAP_HEIGHT = 24;



    //File Paths/Strings
    public static final String GROUND_FLOOR_MAP = "iteration 1 - Ground floor.tmx";
    public static final String STREET_VIEW_MAP = "iteration 1 - Street view.tmx";
    public static final String FIRST_FLOOR_MAP = "iteration 1 - Second floor.tmx";
    public static final String DAY_ICON = "icons8-calendar-30.png";
    public static final String MENU_ICON = "icons8-menu-64.png";
    public static final String TIME_ICON = "icons8-clock-24.png";
    public static final String BUTTONG_BG = "Button.png";

    //Temporary Data
    public static final String CAR_TEXTURE = "Car base-4.png.png";

    //Screen Data
    public static final ArrayList<ScreenDisplay> ALL_SCREENS = new ArrayList<ScreenDisplay>(){{
        add(ScreenDisplay.GROUND);
        add(ScreenDisplay.STREET);
    }};

    //Mapping Out
    public static final String PASSABLE_STRING = "Passable";


//Flag Things
    //Different Flag types
    public static final String[] TRIGGERS = new String[]{"WATER","FOOD","LIGHT","AC","TRASH"};



    //Base odds for each type of flag
    public static final HashMap<String,Integer> BASE_ODDS = new HashMap<String,Integer>(){{
        put("WATER", 0);
        put("FOOD", 0);
        put("LIGHT", 0);
        put("AC", 0);
        put("TRASH", 0);
    }};
    //Base multiplier for each type of flag
    public static final HashMap<String, Double> BASE_MULTI = new HashMap<String,Double>(){{
        put("WATER", 1.0);
        put("FOOD", 1.0);
        put("LIGHT", 1.0);
        put("AC", 1.0);
        put("TRASH", 1.0);
    }};
    //Base points for each type of flag
    public static final HashMap<String,Integer> BASE_POINTS = new HashMap<String,Integer>(){{
        put("WATER", 10);
        put("FOOD", 10);
        put("LIGHT", 10);
        put("AC", 10);
        put("TRASH", 10);
    }};
    //Ground floor positions
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
    //Street flag positions
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
    //First floor positions
    public static final ArrayList<int[]> FIRST_FLOOR_POSITIONS = new ArrayList<int[]>(){{
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
    //Maximum amount of flags on a screen
    public static final int FULL = 11;
    //Amount multiplier grows by with each purchase
    public static final double MULTI_GROWTH = 0.2;
    //Base class price
    public static final int CLASS_PRICE = 50;
    //Base infrastructure price
    public static final int INFRA_PRICE = 150;


    //Saving Constants
    public static final String SAVE_FILE = "PLAYER-SAVE";
    public static final String FILE_EXISTS = "FILE_EXISTS";
    public static final String DAY_NUM = "DAY_NUM";
    public static final String POINTS = "POINTS";
    public static final String INFRA_COUNT = "INFRA_COUNT";
    public static final String CLASS_COUNT = "CLASS_COUNT";
    public static final String INFRA_PURCHASE = "INFRA_PURCHASE";
    public static final String MULTI = "MULTI";
    public static final String ODDS = "ODDS";

//Sprite Constants
    //Atlas
    public static final String ATLAS_FILENAME = "sprite.atlas";
    //Flags
    public static final String WATER_STRING = "Water";
    public static final int WATER_ANIMEND = 3;
    public static final String FOOD_STRING = "Food";
    public static final int FOOD_ANIMEND = 9;
    public static final String LIGHT_STRING = "Light";
    public static final int LIGHT_ANIMEND = 6;
    public static final String AC_STRING = "AC";
    public static final int AC_ANIMEND = 5;
    public static final String TRASH_STRING = "Trash";
    public static final int TRASH_ANIMEND = 5;
    //Navi
    public static final String NAVI_SIDE_TEXTURE = "Navi Side-1.png.png";
    public static final String NAVI_VERT_TEXTURE = "";
    //Background
    public static final String PERSON_STRING = "Person";
    public static final String CAR_STRING = "Car";


}
