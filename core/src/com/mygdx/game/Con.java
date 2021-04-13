package com.mygdx.game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class Con {
    //Sizes
    public static final int WIDTH = 560; //Viewport width
    public static final int HEIGHT = 384; //Viewport height



    //File Paths
    public static final String GROUND_FLOOR_MAP = "iteration 1 - Ground floor.tmx";
    public static final String STREET_VIEW_MAP = "iteration 1 - Street view.tmx";
    public static final String DAY_ICON = "icons8-calendar-30.png";
    public static final String SETTINGS_ICON = "icons8-settings-48.png";
    public static final String TIME_ICON = "icons8-clock-24.png";

    //Temporary Data
    public static final String NAVI_SIDE_TEXTURE = "Navi Side-1.png.png";
    public static final int NAVI_WIDTH = 25;
    public static final int NAVI_HEIGHT = 25;
    public static final int STREET_NAVI_Y = 168;
    public static final int FINAL_HOUR = 16;
    public static final HashMap<ScreenDisplay,String> STRING_TO_MAP_DICT = new HashMap<ScreenDisplay,String>(){{
        put(ScreenDisplay.GROUND, GROUND_FLOOR_MAP);
        put(ScreenDisplay.STREET, STREET_VIEW_MAP);
    }};
    public static final ArrayList<ScreenDisplay> ALL_SCREENS = new ArrayList<ScreenDisplay>(){{
        add(ScreenDisplay.GROUND);
        add(ScreenDisplay.STREET);
    }};

}
