package Model.Mules;

import Model.Mule;
import Model.Player;
import main.Constants;

/**
 * Created by Nate on 10/25/2015.
 */
public class Mule_Food extends Mule {

    public Mule_Food(Player p){
        super(p);
    }

    @Override
    public final void produce() {
        getOwner().changeFood(getLocation().produceFood());
    }

    @Override
    public final String getType() {
        return "Food";
    }

    @Override
    public final int getPrice() {
        return Constants.STORE_PRICE_MULE + Constants.STORE_UPGRADEPRICE_FOOD;
    }

}
