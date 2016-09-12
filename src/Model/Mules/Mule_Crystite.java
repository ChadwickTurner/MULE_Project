package Model.Mules;

import Model.Mule;
import Model.Player;
import main.Constants;

/**
 * Created by Chad on 12/1/2015.
 */
public class Mule_Crystite extends Mule {

    public Mule_Crystite (Player p){
        super(p);
    }

    @Override
    public final void produce() {
        getOwner().changeCrystite(getLocation().produceEnergy());
    }

    @Override
    public final String getType() {
        return "Crystite";
    }

    @Override
    public final int getPrice() {
        return Constants.STORE_PRICE_MULE + Constants.STORE_UPGRADEPRICE_CRYSTITE;
    }
}
