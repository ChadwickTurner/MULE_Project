package Model.Locations;

import Model.Location;
import main.Constants;


/**
 * Created by Nate on 10/25/2015.
 */
public class Location_Crystite extends Location {
    @Override
    public final String getType() {
        return Constants.CRYSTITE_STR;
    }

    @Override
    public final String getShortType() {
        return "c";
    }

    @Override
    public final int produceFood() {
        return 0;
    }

    @Override
    public final int produceEnergy() {
        return 1;
    }

    @Override
    public final int produceSmithore() {
        return 2;
    }


    @Override
    public int produceCrystite() {
        return 5;
    }
}