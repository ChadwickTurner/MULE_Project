package Model.Locations;

import Model.Location;
import main.Constants;

import java.util.Random;

/**
 * Created by Nate on 10/25/2015.
 */
public class Location_Mountain2 extends Location {
    @Override
    public final String getType() {
        return Constants.MOUNTAIN2_STR;
    }

    @Override
    public final String getShortType() {
        return "m2";
    }

    @Override
    public final int produceFood() {
        return 1;
    }

    @Override
    public final int produceEnergy() {
        return 1;
    }

    @Override
    public final int produceSmithore() {
        return 1+2;
    }

    @Override
    public int produceCrystite() {
        Random rand = new Random();
        return rand.nextInt(1);
    }
}
