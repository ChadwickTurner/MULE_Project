package Model.Difficulty;

/**
 * Created by Nate on 12/3/2015.
 */
public abstract class Difficulty {
    public abstract int getPlayerStartFood();
    public abstract int getPlayerStartEnergy();
    public abstract int getPlayerStartSmithore();
    public abstract int getPlayerStartCrystilite();

    public abstract int getStoreStartFood();
    public abstract int getStoreStartEnergy();
    public abstract int getStoreStartSmithore();
    public abstract int getStoreStartCrystilite();
    public abstract int getStoreStartMule();

    public abstract String getString();
}
