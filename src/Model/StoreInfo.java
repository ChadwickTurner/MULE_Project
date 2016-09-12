package Model;

/**
 * Created by Chad on 10/16/2015.
 */
public class StoreInfo {

    private int food;
    private int energy;
    private int smithore;
    private int mule;
    private int crystite;

    public StoreInfo(Store s) {
        food = s.getFoodQuantity();
        energy = s.getEnergyQuantity();
        smithore = s.getSmithoreQuantity();
        mule = s.getMuleQuantity();
        crystite = s.getCrystiteQuantity();
    }

    public final int getFood() {
        return food;
    }

    public final int getEnergy() {
        return energy;
    }

    public final int getSmithore() {
        return smithore;
    }

    public final int getMules() {
        return mule;
    }

    public final int getCrystite() {
        return crystite;
    }
}
