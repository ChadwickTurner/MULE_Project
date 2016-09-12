package Model;

/**
 * Created by Nathan on 9/17/2015.
 */
public abstract class Location {
    private Player owner;
    private Mule mule;


    public Location(){
        owner = null;
        mule = null;
    }

    public abstract String getType();
    public abstract String getShortType();

    public abstract int produceFood();
    public abstract int produceEnergy();
    public abstract int produceSmithore();
    public abstract int produceCrystite();

    public final Player getOwner() {
        return owner;
    }

    public final Mule getMule() {
        return mule;
    }

    public final void setMule(Mule muleV) {
        this.mule = muleV;
    }

    public final void setOwner(Player ownerP) {
        this.owner = ownerP;
    }


}
