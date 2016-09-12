package main;

import java.util.Collections;

/**
 * Created by Nathan on 9/17/2015.
 */
public final class Constants {

    private Constants(){
        throw new AssertionError("Instantiating utility class...");
    }

    public static final int MAP_HEIGHT = 5;
    public static final int MAP_WIDTH = 9;

    public static final int TOWN_PX_HEIGHT = 600;
    public static final int TOWN_PX_WIDTH = 800;
    public static final int TOWN_BUMPER_PX_WIDTH = 75;
    public static final int SPRITE_PX_SIZE = 40;

    public static final long TURN_MILLIS = 50000;

    public static final int LAND_COST = 300;

    public static final int PIXELS_PER_MOVEMENT = 8;

    public static final int STORE_QUANTITY_FOOD = 8;
    public static final int STORE_QUANTITY_ENERGY = 8;
    public static final int STORE_QUANTITY_SMITHORE = 8;
    public static final int STORE_QUANTITY_MULE = 14;

    public static final int STORE_PRICE_FOOD = 30;
    public static final int STORE_PRICE_ENERGY = 25;
    public static final int STORE_PRICE_SMITHORE = 50;
    public static final int STORE_PRICE_CRYTSTITE = 50;
    public static final int STORE_PRICE_MULE = 100;

    public static final int STORE_UPGRADEPRICE_FOOD = 25;
    public static final int STORE_UPGRADEPRICE_ENERGY = 50;
    public static final int STORE_UPGRADEPRICE_SMITHORE = 75;
    public static final int STORE_UPGRADEPRICE_CRYSTITE = 100;

    public static final int[] PUB_ROUNDBONUS = {50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200};
    public static final int[] PUB_TIMEBONUS = {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 100, 100, 100, 100,
            100, 100, 100, 100, 100, 100, 100, 100, 100, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150,
            200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200};
    
    public static final String PLAINS_STR = "Plain";
    public static final String RIVER_STR = "River";
    public static final String TOWN_STR = "Town";
    public static final String MOUNTAIN1_STR = "Mountain1";
    public static final String MOUNTAIN2_STR = "Mountain2";
    public static final String MOUNTAIN3_STR = "Mountain3";
    public static final String CRYSTITE_STR = "Crystite";

    public static final String[][] DEFAULT_MAP = {
            {PLAINS_STR,    PLAINS_STR,    MOUNTAIN1_STR, PLAINS_STR, RIVER_STR, PLAINS_STR, MOUNTAIN3_STR, PLAINS_STR, PLAINS_STR},
            {PLAINS_STR,    MOUNTAIN1_STR, PLAINS_STR,    PLAINS_STR, RIVER_STR, PLAINS_STR, PLAINS_STR,    PLAINS_STR, MOUNTAIN3_STR},
            {MOUNTAIN3_STR, PLAINS_STR,    PLAINS_STR,    PLAINS_STR, TOWN_STR,  PLAINS_STR, PLAINS_STR,    PLAINS_STR, MOUNTAIN1_STR},
            {PLAINS_STR,    MOUNTAIN2_STR, PLAINS_STR,    PLAINS_STR, RIVER_STR, PLAINS_STR, MOUNTAIN2_STR, PLAINS_STR, PLAINS_STR},
            {PLAINS_STR,    PLAINS_STR,    MOUNTAIN2_STR, PLAINS_STR, RIVER_STR, PLAINS_STR, PLAINS_STR,    PLAINS_STR, MOUNTAIN2_STR}
    };

    public static final String[] RAND_WEIGH_LOC_STRS = {
            Constants.PLAINS_STR, Constants.PLAINS_STR, Constants.PLAINS_STR, Constants.PLAINS_STR, Constants.PLAINS_STR,
            Constants.PLAINS_STR, Constants.MOUNTAIN1_STR, Constants.MOUNTAIN2_STR, Constants.MOUNTAIN3_STR, Constants.CRYSTITE_STR};

    public static final String IMAGE_FILE_TYPE = ".png";
    public static final String SPRITE_LOCATION = "/View/images/Sprites/";

    public static final int IMAGE_RGB_OFFSET = 16777216;

    public static final int PLAYER_START_FOOD = 8;
    public static final int PLAYER_START_ENERGY = 4;

    public static final int PLAYER_START_MONEY = 1000;
    public static final int PLAYER_START_MONEY_HUMAN = 600;
    public static final int PLAYER_START_MONEY_FLAPPER = 1600;

    public static final String BEGINNER_STR = "Beginner";
    public static final String STANDARD_STR = "Standard";
    public static final String TOURNAMENT_STR = "Tournament";

}
