package Model.Mules;

import Model.Mule;
import Model.Player;
import main.Constants;

/**
 * Created by Nate on 10/25/2015.
 */
public class Mule_Energy extends Mule {

    public Mule_Energy(Player p){
        super(p);
    }

    @Override
    public final void produce() {
        getOwner().changeEnergy(getLocation().produceEnergy());
    }

    @Override
    public final String getType() {
        return "Energy";
    }

    @Override
    public final int getPrice() {
        return Constants.STORE_PRICE_MULE + Constants.STORE_UPGRADEPRICE_ENERGY;
    }
}
