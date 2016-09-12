package Model;

/**
 * Created by Chad on 10/16/2015.
 */
public class PlayerInfo {

    private String playerName;
    private Player.Race playerRace;
    private Player.Color playerColor;
    private int food;
    private int energy;
    private int smithore;
    private int crystite;
    private int money;

    public PlayerInfo(Player p) {
        playerName = p.getName();
        playerRace = p.getRace();
        playerColor = p.getColor();
        food = p.getFood();
        energy = p.getEnergy();
        smithore = p.getSmithore();
        crystite = p.getCrystite();
        money = p.getMoney();
    }

    public final String getPlayerName() {
        return playerName;
    }

    public final Player.Race getPlayerRace() {
        return playerRace;
    }

    public final Player.Color getPlayerColor() {
        return playerColor;
    }

    public final int getFood() {
        return food;
    }

    public final int getSmithore() {
        return smithore;
    }

    public final int getEnergy() {
        return energy;
    }

    public final int getCrystite() {
        return crystite;
    }

    public final int getMoney() {
        return money;
    }
}
