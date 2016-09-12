package Controller;

import Model.GameSettings;
import Model.Map;
import Model.Player;
import View.GUIController;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import main.Constants;

import java.util.Objects;

/**
 * Created by Nathan on 9/17/2015.
 */
public final class MapController implements Runnable {

    private static MapController controller = new MapController();

    private MapController() {
        GameSettings.getInstance().newMap();
    }

    public static MapController getInstance() {
        return controller;
    }

    public void run() {
        GUIController.getInstance().setMode(GUIController.Mode.MAP);
        runLandGrab();
        GameSettings.getInstance().setMode(GameSettings.Mode.Turns);
        GameSettings.getInstance().setCurr(0);
        new Thread(TurnController.getInstance()).start();
    }

    private void runLandGrab() {
        GameSettings.getInstance().reorderPlayers();
        do {
            Player player = GameSettings.getInstance().getCurrent();
            Point2D cursor = new Point2D(0, 0);
            GUIController.getInstance().mapMoveCursor(cursor);
            GUIController.getInstance().alert("", "Land Grab: " + player.getName());

            Event e;
            boolean complete = false;
            do {
                e = GUIController.getInstance().getEvent();
                if (e instanceof KeyEvent) {
                    cursor = Util.navigateInvertVertical(((KeyEvent) e), cursor,
                            0, GameSettings.getInstance().getMap().getHeight() - 1,
                            0, GameSettings.getInstance().getMap().getWidth() - 1, 1);
                    complete = processInput(((KeyEvent)e), player, cursor);
                }
            } while (!complete);
        }while (GameSettings.getInstance().nextTurn());
    }

    private boolean processInput(KeyEvent e, Player player, Point2D cursor){
        String button = e.getCode().getName();
        GUIController.getInstance().mapMoveCursor(cursor);
        if (button.equals("Enter") &&
                (GameSettings.getInstance().getMap().getLocation(cursor).getOwner() != null ||
                        Objects.equals(GameSettings.getInstance().getMap().getLocation(cursor).getType(), "Town"))) {
            GUIController.getInstance().alert("", "That Location cannot be purchased ");
        } else if (button.equals("Enter") && player.getMoney() < Constants.LAND_COST && GameSettings.getInstance().getRound() > 2) {
            GUIController.getInstance().alert("", "You do not have sufficient funds to purchase land.");
        } else if (button.equals("Enter")) {
            endTurn(true, player, cursor);
            return true;
        }
        if (button.equals("P")) {
            endTurn(false, player, cursor);
            return true;
        }
        return false;
    }

    private void endTurn(boolean validPurchase, Player player, Point2D cursor){
        if (validPurchase && GameSettings.getInstance().getRound() > 2) {
            GameSettings.getInstance().getMap().getLocation(cursor).setOwner(player);
            player.changeMoney(-Constants.LAND_COST);
            GUIController.getInstance().mapUpdateBorderInfo();
            GUIController.getInstance().mapRedrawTile(cursor);
        } else if (validPurchase) {
            GameSettings.getInstance().getMap().getLocation(cursor).setOwner(player);
            GUIController.getInstance().mapRedrawTile(cursor);
        }
    }

}
