package Model.Difficulty;

import main.Constants;

/**
 * Created by Nate on 12/3/2015.
 */
public class Difficulty_Beginner extends Difficulty {

    @Override
    public int getPlayerStartFood() {
        return 8;
    }

    @Override
    public int getPlayerStartEnergy() {
        return 4;
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
        return 16;
    }

    @Override
    public int getStoreStartEnergy() {
        return 16;
    }

    @Override
    public int getStoreStartSmithore() {
        return 0;
    }

    @Override
    public int getStoreStartCrystilite() {
        return 0;
    }

    @Override
    public int getStoreStartMule() {
        return 25;
    }

    @Override
    public String getString() {
        return Constants.BEGINNER_STR;
    }
}
