package Controller;

import Model.RandomEvent;
import View.GUIController;
import javafx.event.ActionEvent;
import main.Constants;
import Model.GameSettings;
import Model.Player;

/**
 * Created by Nathan on 9/30/2015.
 */
public final class TurnController implements Runnable {
    private static TurnController controller = new TurnController();

    private volatile long currentTime;
    private volatile boolean startMid = false;
    private volatile long startTime;

    private static final int MILLISECONDS = 1000;

    public static TurnController getInstance() {
        return controller;
    }

    private TurnController() {
    }

    public void startMid(int time){
        startTime = time * MILLISECONDS;
        startMid = true;
    }

    @Override
    public void run() {
        GameSettings.getInstance().reorderPlayers();
        GameSettings.getInstance().getPlayer(0).setLastPlace(true);
        do {
            Player current = GameSettings.getInstance().getCurrent();
            GUIController.getInstance().alert("", "Starting " + current.getName() + "'s Turn");
            if(!startMid) {
                RandomEvent.runEvent(GameSettings.getInstance().getCurrent(), GameSettings.getInstance().getRound());
                GUIController.getInstance().getEvent();
                startTime = Constants.TURN_MILLIS;
            } else {
                startMid = false;
            }
            currentTime = startTime;
            Turn turn = new Turn(current, currentTime);
            Thread turnThread = new Thread(turn);
            long start = System.currentTimeMillis();
            turnThread.start();
            do {
                long x = currentTime;
                do {
                    currentTime = startTime - (System.currentTimeMillis() - start);
                }while(x-currentTime < MILLISECONDS);
                GUIController.getInstance().turnDecrementTimer();
                turn.updateTime(currentTime);
            } while (currentTime > 0 && turnThread.isAlive());
            turn.kill();
            GUIController.getInstance().sendEvent(new ActionEvent()); //Send event to fire and cause turn to kill itself
        } while (GameSettings.getInstance().nextTurn());
        GameSettings.getInstance().produceAll();
        GameSettings.getInstance().setCurr(0);
        GameSettings.getInstance().setMode(GameSettings.Mode.LandGrab);
        GameSettings.getInstance().getPlayer(0).setLastPlace(false);
        GameSettings.getInstance().nextRound();
        new Thread(MapController.getInstance()).start();

    }

    public int getTime() {
        return (int)currentTime/ MILLISECONDS;
    }
}
