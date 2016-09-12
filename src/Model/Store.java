package Model;

import Model.Mules.MuleFactory;
import main.Constants;

/**
 * Created by Nate on 10/8/2015.
 */
public final class Store {
    private static Store instance = new Store();

    private int foodQuantity;
    private int energyQuantity;
    private int smithoreQuantity;
    private int crystiteQuantity;
    private int muleQuantity;

    private Store(){
        foodQuantity = GameSettings.getInstance().getDifficulty().getStoreStartFood();
        energyQuantity = GameSettings.getInstance().getDifficulty().getStoreStartEnergy();
        smithoreQuantity = GameSettings.getInstance().getDifficulty().getStoreStartSmithore();
        crystiteQuantity = GameSettings.getInstance().getDifficulty().getStoreStartCrystilite();
        muleQuantity = GameSettings.getInstance().getDifficulty().getStoreStartMule();
    }

    public static  Store getInstance(){
        return instance;
    }

    public void newGame(int food, int energy, int smithore, int crystite, int mule){
        foodQuantity = food;
        energyQuantity = energy;
        smithoreQuantity = smithore;
        crystiteQuantity = crystite;
        muleQuantity = mule;
    }

    public boolean purchaseFood(Player p, int quantity){
        if (foodQuantity < quantity || p.getMoney() < Constants.STORE_PRICE_FOOD * quantity){
            return false;
        }
        p.changeMoney(-1 * Constants.STORE_PRICE_FOOD * quantity);
        p.changeFood(quantity);
        foodQuantity -= quantity;
        return true;
    }

    public boolean sellFood(Player p, int quantity){
        if(p.getFood() < quantity){
            return false;
        }
        p.changeMoney(Constants.STORE_PRICE_FOOD * quantity);
        p.changeFood(-1 * quantity);
        foodQuantity += quantity;
        return true;
    }

    public boolean purchaseEnergy(Player p, int quantity){
        if (energyQuantity < quantity || p.getMoney() < Constants.STORE_PRICE_ENERGY * quantity){
            return false;
        }
        p.changeMoney(-1 * Constants.STORE_PRICE_ENERGY * quantity);
        p.changeEnergy(quantity);
        energyQuantity -= quantity;
        return true;
    }

    public boolean sellEnergy(Player p, int quantity){
        if(p.getEnergy() < quantity){
            return false;
        }
        p.changeMoney(Constants.STORE_PRICE_ENERGY * quantity);
        p.changeEnergy(-1 * quantity);
        energyQuantity += quantity;
        return true;
    }

    public boolean purchaseSmithore(Player p, int quantity){
        if (smithoreQuantity < quantity || p.getMoney() < Constants.STORE_PRICE_SMITHORE * quantity){
            return false;
        }
        p.changeMoney(-1 * Constants.STORE_PRICE_SMITHORE * quantity);
        p.changeSmithore(quantity);
        smithoreQuantity -= quantity;
        return true;
    }

    public boolean sellSmithore(Player p, int quantity){
        if(p.getSmithore() < quantity){
            return false;
        }
        p.changeMoney(Constants.STORE_PRICE_SMITHORE * quantity);
        p.changeSmithore(-1 * quantity);
        smithoreQuantity += quantity;
        return true;
    }

    public boolean purchaseCrystite(Player p, int quantity){
        if (crystiteQuantity < quantity || p.getMoney() < Constants.STORE_PRICE_CRYTSTITE * quantity){
            return false;
        }
        p.changeMoney(-1 * Constants.STORE_PRICE_CRYTSTITE * quantity);
        p.changeCrystite(quantity);
        crystiteQuantity -= quantity;
        return true;
    }

    public boolean sellCrystite(Player p, int quantity){
        if(p.getCrystite() < quantity){
            return false;
        }
        p.changeMoney(Constants.STORE_PRICE_CRYTSTITE * quantity);
        p.changeCrystite(-1 * quantity);
        crystiteQuantity += quantity;
        return true;
    }

    public boolean purchaseMule(Player p, String type){
        if (muleQuantity <= 0 || p.getMoney() < Constants.STORE_PRICE_MULE){
            return false;
        }
        p.setCurrentMule(MuleFactory.createMule(p, type));
        p.changeMoney(-1 * p.getCurrentMule().getPrice());
        muleQuantity -= 1;
        return true;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public int getEnergyQuantity() {
        return energyQuantity;
    }

    public int getSmithoreQuantity() {
        return smithoreQuantity;
    }

    public int getCrystiteQuantity() {
        return crystiteQuantity;
    }

    public int getMuleQuantity() {
        return muleQuantity;
    }
}
