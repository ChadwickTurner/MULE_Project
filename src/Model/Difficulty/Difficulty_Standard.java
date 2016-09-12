package Model.Difficulty;

import main.Constants;

/**
 * Created by Nate on 12/3/2015.
 */
public class Difficulty_Standard extends Difficulty {
    @Override
    public int getPlayerStartFood() {
        return 4;
    }

    @Override
    public int getPlayerStartEnergy() {
        return 2;
    }

    @Override
    public int getPlayerStartSmithore() {
        return 0;
    }

    @Override
    public int getPlayerStartCrystilite() {
        return 0;
    }

    @Override
    public int getStoreStartFood() {
        return 8;
    }

    @Override
    public int getStoreStartEnergy() {
        return 8;
    }

    @Override
    public int getStoreStartSmithore() {
        return 8;
    }

    @Override
    public int getStoreStartCrystilite() {
        return 0;
    }

    @Override
    public int getStoreStartMule() {
        return 14;
    }

    @Override
    public String getString() {
        return Constants.STANDARD_STR;
    }


}
