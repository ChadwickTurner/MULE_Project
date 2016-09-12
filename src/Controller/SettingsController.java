package Controller;

import View.GUIController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import Model.GameSettings;

/**
 * Created by Nathan on 9/18/2015.
 */
public final class SettingsController implements Runnable {
    private volatile boolean kill = false;
    private static SettingsController controller = new SettingsController();

    public static SettingsController getInstance() {
        return controller;
    }

    private SettingsController() {

    }

    public void kill(){
        kill = true;
    }

    public void run() {
        GUIController.getInstance().setMode(GUIController.Mode.SETUP);

        Event e = null;
        do {
            e = GUIController.getInstance().getEvent();
        } while (!(e instanceof ActionEvent));

        if(kill){
            kill = false;
            return;
        }

        GameSettings.getInstance().setMode(GameSettings.Mode.LandGrab);
        GameSettings.getInstance().setCurr(0);
        new Thread(MapController.getInstance()).start();
    }
}
