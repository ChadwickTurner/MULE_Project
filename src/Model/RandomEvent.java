package Model;
import View.GUIController;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Galen Dockman on 10/23/2015.
 */
public final class RandomEvent {
    private static final int COOL_NUM_1 = 2800;
    private static final int COOL_NUM_2 = 756;
    private static final int COOL_NUM_3 = 649;
    private static final int COOL_NUM_4 = 541;
    private static final int COOL_NUM_5 = 433;
    private static final int COOL_NUM_6 = 325;
    private static final int COOL_NUM_7 = 217;
    private static final int COOL_NUM_8 = 109;
    private static final int COOL_NUM_9 = 568;
    private static final int COOL_NUM_10 = 379;
    private static final int COOL_NUM_11 = 190;
    private static final int COOL_NUM_12 = 3;
    private static final int COOL_NUM_13 = 8;
    private static final int COOL_NUM_14 = 4;
    private static final int COOL_NUM_15 = 6;


    private RandomEvent(){
        throw new AssertionError("Instantiating utility class...");
    }

    public static void runEvent(Player player, int round) {
        Random rand = new Random();
        int eventChance = rand.nextInt(COOL_NUM_1) + 1;
        if (eventChance <= COOL_NUM_2 && eventChance >=1) {
            if (player.isLastPlace()) {
                lastPlace(player, eventChance, round);
            } else {
                if (isBetween(COOL_NUM_2, COOL_NUM_3, eventChance)) {
                    event1(player);
                } else if (isBetween(COOL_NUM_3 - 1, COOL_NUM_4, eventChance)) {
                    event2(player);
                } else if (isBetween(COOL_NUM_4 - 1, COOL_NUM_5, eventChance)) {
                    event3(player, round);
                } else if (isBetween(COOL_NUM_5 - 1, COOL_NUM_6, eventChance)) {
                    event4(player, round);
                } else if (isBetween(COOL_NUM_6 - 1, COOL_NUM_7, eventChance)) {
                    event5(player, round);
                } else if (isBetween(COOL_NUM_7 - 1, COOL_NUM_8, eventChance)) {
                    event6(player);
                } else if (isBetween(COOL_NUM_8 - 1, 40, eventChance)) {
                    event7(player, round);
                } else if (isBetween(39, 20, eventChance)){
                    event8(round);
                } else {
                    event9();
                }
            }
        } else {
                GUIController.getInstance().alert("", "Nothing Special Happens This Turn.");
        }
    }

    private static void lastPlace(Player player, int eventChance, int round){
        if (isBetween(COOL_NUM_2, COOL_NUM_9, eventChance)) {
            event1(player);
        } else if (isBetween(COOL_NUM_9 - 1, COOL_NUM_10, eventChance)) {
            event2(player);
        } else if (isBetween(COOL_NUM_10 - 1, COOL_NUM_11, eventChance)) {
            event3(player, round);
        } else {
            event4(player, round);
        }
    }

    private static void event1(Player player) {
        GUIController.getInstance().alert("", "YOU JUST RECEIVED A PACKAGE FROM THE GT ALUMNI CONTAINING 3 FOOD AND 2 ENERGY UNITS.");
        player.changeFood(COOL_NUM_12);
        player.changeEnergy(2);
    }

    private static void event2(Player player) {
        GUIController.getInstance().alert("", "A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.");
        player.changeSmithore(2);
    }

    private static void event3(Player player, int round) {
        GUIController.getInstance().alert("", "THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $" + (COOL_NUM_13 * round) + ".");
        player.changeMoney(COOL_NUM_13 * round);
    }

    private static void event4(Player player, int round) {
        GUIController.getInstance().alert("", "YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $" + (2 * round) + ".");
        player.changeMoney(2 * round);
    }

    private static void event5(Player player, int round) {
        GUIController.getInstance().alert("", "FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $" + (COOL_NUM_14 * round) + ".");
        player.changeMoney(-1 * COOL_NUM_14 * round);
    }

    private static void event6(Player player) {
        GUIController.getInstance().alert("", "MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE SHED AND STOLE HALF YOUR FOOD.");
        player.changeFood(player.getFood() / (-1 * 2));
    }

    private static void event7(Player player, int round) {
        GUIController.getInstance().alert("", "YOUR SPACE GYPSY INLAWS MADE A MESS OF THE TOWN. IT COST YOU $" + (COOL_NUM_15 * round) + " TO CLEAN IT UP.");
        player.changeMoney(-1 * COOL_NUM_15 * round);
    }

    private static void event8(int round) {
        GUIController.getInstance().alert("", "EVERYONE DECIDED TO PITCH IN TO BUY A SPACE SHIP. EVERYONE PAYS $" + (COOL_NUM_15 * round) + ". IT TURNED OUT TO BE A MODEL.");
        ArrayList<Player> pl = GameSettings.getInstance().getPlayers();
        for (Player p: pl) {
            p.changeMoney(-1 * COOL_NUM_15 * round);
        }
    }

    private static void event9() {
        GUIController.getInstance().alert("", "EXTRA CRYSTITE WAS FOUND. EVERYONE GETS A CRYSTITE GEM.");
        ArrayList<Player> pl = GameSettings.getInstance().getPlayers();
        for (Player p: pl) {
            p.changeCrystite(1);
        }
    }

    private static boolean isBetween(int max, int min, int x) {
        return (x <= max && x >= min);
    }
}
