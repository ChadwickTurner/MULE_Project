package Model.Locations;

import Model.Location;
import main.Constants;

/**
 * Created by weisk on 10/26/2015.
 */
public final class LocationFactory {

    private LocationFactory(){
        throw new AssertionError("Instantiating utility class...");
    }

    public static Location create(String type){
        switch(type){
            case Constants.RIVER_STR:
                return new Location_River();
            case Constants.PLAINS_STR:
                return new Location_Plain();
            case Constants.TOWN_STR:
                return new Location_Town();
            case Constants.MOUNTAIN1_STR:
                return new Location_Mountain1();
            case Constants.MOUNTAIN2_STR:
                return new Location_Mountain2();
            case Constants.MOUNTAIN3_STR:
                return new Location_Mountain3();
            case Constants.CRYSTITE_STR:
                return new Location_Crystite();
            default: break;
        }
        return null;
    }
}
