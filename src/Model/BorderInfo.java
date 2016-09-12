package Model;

import Controller.TurnController;

/**
 * Created by Chad on 10/16/2015.
 */
public final class BorderInfo {

    private BorderInfo(){
        throw new AssertionError("Instantiating utility class...");
    }

    public static int getNumPlayers() {
        return GameSettings.getInstance().getPlayers().size();
    }

    public static int getPlayerNum()
    {
        return GameSettings.getInstance().getCurr();
    }

    public static PlayerInfo getCurrentPlayer()
    {
        return new PlayerInfo(GameSettings.getInstance().getCurrent());
    }

    public static PlayerInfo getPlayerInfo(int playerNum) {
        return new PlayerInfo(GameSettings.getInstance().getPlayersOriginalOrder().get(playerNum));
    }

    public static StoreInfo getStoreInfo() {
        return new StoreInfo(Store.getInstance());
    }

    public static String getMode() {
        String output = GameSettings.getInstance().getMode().toString();
        for (int i = 1; i < output.length(); i++) {
            if (output.charAt(i) >= (int)'A' && output.charAt(i) <= (int)'Z') {
                output = output.substring(0, i) + ' ' + output.substring(i++);
            }
        }
        return output;
    }

    public static int getTimeLeft() {
        return TurnController.getInstance().getTime();
    }
}
