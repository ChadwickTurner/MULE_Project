package Model.Mules;

import Model.Mule;
import Model.Player;
import main.Constants;

/**
 * Created by Nate on 10/25/2015.
 */
public class Mule_Smithore extends Mule {

    public Mule_Smithore(Player p){
        super(p);
    }

    @Override
    public final void produce() {
        getOwner().changeSmithore(getLocation().produceSmithore());
    }

    @Override
    public final String getType() {
        return "Smithore";
    }

    @Override
    public final int getPrice() {
        return Constants.STORE_PRICE_MULE + Constants.STORE_UPGRADEPRICE_SMITHORE;
    }
}
