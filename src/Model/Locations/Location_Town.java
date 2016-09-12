package Model.Locations;

import Model.Location;
import main.Constants;

import java.util.Random;

/**
 * Created by Nate on 10/25/2015.
 */
public class Location_Town extends Location{

    @Override
    public final String getType() {
        return Constants.TOWN_STR;
    }

    @Override
    public final String getShortType() {
        return "t";
    }

    @Override
    public final int produceFood() {
        return 0;
    }

    @Override
    public final int produceEnergy() {
        return 0;
    }

    @Override
    public final int produceSmithore() {
        return 0;
    }

    @Override
    public int produceCrystite() {
        Random rand = new Random();
        return rand.nextInt(1);
    }
}
