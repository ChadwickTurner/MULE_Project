package Model;

/**
 * Created by Nathan on 9/17/2015.
 */
public abstract class Mule {

    private Player owner;
    private Location location;

    public Mule(Player p){
        owner = p;
    }

    public final void setLocation(Location loc){this.location = loc;}

    /**
     * produces resources based on the type of mule, and the type of land
     * updates the resources in player class
     */
    public abstract void produce();

    public abstract String getType();

    public abstract int getPrice();

    public final Player getOwner(){
        return owner;
    }

    public final Location getLocation(){
        return location;
    }

}
