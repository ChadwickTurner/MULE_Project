package Model.Mules;

import Model.Player;
import Model.Mule;

/**
 * Created by weisk on 10/26/2015.
 */
public final class MuleFactory {

    private MuleFactory(){
        throw new AssertionError("Instantiating utility class...");
    }

    public static Mule createMule(Player p, String type){
        switch(type){
            case "Food":
                return new Mule_Food(p);
            case "Energy":
                return new Mule_Energy(p);
            case "Smithore":
                return new Mule_Smithore(p);
            case "Crystite":
                return new Mule_Crystite(p);
            default: break;
        }
        return null;
    }
}
