package Model;

import main.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathan on 9/10/2015.
 */
public class Player {
    /**
     * List of available player colors
     */
    public enum Color {
        Blue,
        Red,
        Green,
        Purple
    }

    /**
     * List of abailable player colors
     */
    public enum Race {
        Humanoid,
        Mechtron,
        Bonzoid,
        Flapper,
        Gollumer,
        Packer,
        Spheroid,
        Leggite
    }

    private String name;
    private Color color;
    private Race race;
    private int money;
    private int food = GameSettings.getInstance().getDifficulty().getPlayerStartFood();
    private int energy = GameSettings.getInstance().getDifficulty().getPlayerStartEnergy();
    private int smithore = GameSettings.getInstance().getDifficulty().getPlayerStartSmithore();
    private int crystite = GameSettings.getInstance().getDifficulty().getStoreStartCrystilite();
    private List<Mule> mules = new ArrayList<>();
    private Mule currentMule;
    private boolean lastPlace = false;

    /**
     * Constructor for the player class
     * @param nameP Name of the player
     * @param colorP Color of the player
     * @param raceP Race of the player
     */
    public Player(String nameP, Color colorP, Race raceP){
        initializeMoney(raceP);
        this.name = nameP;
        this.color = colorP;
        this.race = raceP;
        this.food = GameSettings.getInstance().getDifficulty().getPlayerStartFood();
        this.energy = GameSettings.getInstance().getDifficulty().getPlayerStartEnergy();
    }

    public Player(String nameP, Color colorP, Race raceP, int moneyP, int foodP, int energyP, int smithoreP){
        this.name = nameP;
        this.color = colorP;
        this.race = raceP;
        this.money = moneyP;
        this.food = foodP;
        this.energy = energyP;
        this.smithore = smithoreP;
    }

    /**
     *
     * @return the color of the player
     */
    public final Color getColor() {
        return color;
    }

    /**
     *
     * @return the race of the player
     */
    public final Race getRace() {
        return race;
    }

    /**
     *
     * @return the name of the player
     */
    public final String getName() {
        return name;
    }

    public final int getMoney() {
        return money;
    }

    public final int getScore() {
        return money + (food * Constants.STORE_PRICE_ENERGY)
                + (energy * Constants.SPRITE_PX_SIZE)
                + (smithore * Constants.STORE_UPGRADEPRICE_ENERGY);
    }

    public final void changeMoney(int amt) {
        money += amt;
    }

    public final int getFood() {
        return food;
    }

    public final void changeFood(int amt) {
        food += amt;
    }

    public final int getEnergy() {
        return energy;
    }

    public final void changeEnergy(int amt){
        energy += amt;
    }

    public final int getSmithore() {
        return smithore;
    }

    public final void changeSmithore(int amt){
        smithore += amt;
    }

    public int getCrystite() {
        return crystite;
    }

    public void changeCrystite(int crystite) {
        this.crystite += crystite;
    }

    public final Mule getCurrentMule() {
        return currentMule;
    }

    public final void setCurrentMule(Mule mule) {currentMule = mule;}

    public final void addMule(Mule m){
        mules.add(m);
    }

    public final List<Mule> getMules() {
        return mules;
    }

    public final boolean isLastPlace() { return lastPlace; }

    public final void setLastPlace(boolean place) { lastPlace = place; }

    private void initializeMoney(Race raceI) {
        if (raceI.equals(Race.Humanoid)) {
            money = Constants.PLAYER_START_MONEY_HUMAN;
        } else if (raceI.equals(Race.Flapper)) {
            money = Constants.PLAYER_START_MONEY_FLAPPER;
        } else { money = Constants.PLAYER_START_MONEY; }
    }

    /*
    * iterates through the list of mules and produces for all of them
    * if player has at least 1 energy for each
    * After production player loses one energy per mule.
     */
    public final void produceAllMules() {
        if (!mules.isEmpty()) {
            for (Mule m : mules) {
                if (energy > 0) {
                    m.produce();
                    energy--;
                }
            }
        }
    }

    public final boolean settleMule(Location loc){
        if(loc.getOwner() == this && loc.getMule() == null){
            loc.setMule(currentMule);
            currentMule.setLocation(loc);
            addMule(currentMule);
            currentMule = null;
            return true;
        } else {
            mules.remove(currentMule);
            currentMule = null;
            return false;
        }
    }

}
