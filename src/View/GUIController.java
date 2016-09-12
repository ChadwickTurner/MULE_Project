package View;

import Controller.Turn;
import View.map.mapDisplayController;
import View.town.TownDisplayController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import Model.GameSettings;
import Model.Mule;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Nathan on 9/17/2015.
 */
public final class GUIController {
    private static GUIController controller = new GUIController();

    /**
     * List of game modes
     */
    public enum Mode {
        SETUP,
        CONFIG,
        ADD_PLAYER,
        INFO_PAGE,
        MAP,
        TOWN,
        AUCTION,
        PAUSE
    }

    private Mode mode;
    private BlockingQueue<Event> eventQueue;
    private Stage stage;
    private Object currentController;

    public static GUIController getInstance() {
        return controller;
    }

    private GUIController() {
        stage = GameSettings.getInstance().getStage();
        eventQueue = new LinkedBlockingQueue<Event>();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(final Mode nextmode) {
        this.mode = nextmode;
        final String resource;
        switch (mode) {
            case SETUP:
                resource = "../View/setup/homePage.fxml";
                break;
            case CONFIG:
                resource = "../View/setup/configPage.fxml";
                break;
            case ADD_PLAYER:
                resource = "../View/setup/addPlayerPage.fxml";
                break;
            case INFO_PAGE:
                resource = "../View/setup/infoPage.fxml";
                break;
            case MAP:
                resource = "../View/map/mapDisplay.fxml";
                break;
            case TOWN:
                resource = "../View/town/townDisplay.fxml";
                break;
            default:
                resource = "";
                break;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
                try {
                    root = loader.load();
                    currentController = loader.getController();
                } catch (IOException e) { //e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        sendEvent(event);
                    }
                });
                stage.show();
            }
        });

    }

    /**
     * Displays a popup to the user with a message
     * @param title the title for the popup
     * @param message the message to be displayed
     */
    public void alert(final String title, final String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
                sendEvent(new ActionEvent());
            }
        });
    }

    public void mapMoveCursor(final Point2D cursor){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((mapDisplayController) currentController).setCursor(cursor);
            }
        });
    }

    public void mapMovePlayer(final Point2D cursor){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((mapDisplayController) currentController).setPlayer(cursor);
            }
        });
    }

    public void mapDisplayText(final String text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((mapDisplayController) currentController).setDisplay(text);
            }
        });
    }

    public void mapRedrawTile(final Point2D tile){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((mapDisplayController) currentController).redrawTile(tile);
            }
        });
    }

    public void mapUpdateTiles(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((mapDisplayController) currentController).updateBorders();
            }
        });
    }

    public void mapUpdateBorderInfo() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((mapDisplayController) currentController).updateBorderInfo();
            }
        });
    }

    public void townUpdateBorderInfo() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).updateBorderInfo();
            }
        });
    }

    public void townDrawPlayer(final Point2D player){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).drawPlayerSprite(player);
            }
        });
    }

    /**
     * Updates the timer displayed
     */
    public void turnDecrementTimer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((Bordered) currentController).updateTimer();
            }
        });
    }

    public void townSpriteToMule(final String type) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).spriteToMule(type);
            }
        });
    }

    public void townDisplayPopup(Mule m, Turn.TownLoc loc) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).displayPopup(m, loc);
            }
        });
    }

    public void townDisplayPurchaseFail() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).displayPurchaseFail();
            }
        });
    }

    public void townUpdateCost(int cost) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).updateCost(cost);
            }
        });
    }

    public void townUpdateValue(int value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).updateValue(value);
            }
        });
    }

    public int townGetBuyCount() {
        return ((TownDisplayController) currentController).getBuyCount();
    }

    public int townGetSellCount() {
        return ((TownDisplayController) currentController).getSellCount();
    }

    public void townRemovePopup() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).removePopup();
            }
        });
    }

    public void townSetMaxBuyAmount(int amount) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).setMaxBuyAmount(amount);
            }
        });
    }

    public void townSetMaxSellAmount(int amount) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((TownDisplayController) currentController).setMaxSellAmount(amount);
            }
        });
    }


    public void sendEvent(Event e){
        try {
            eventQueue.put(e);
        } catch (InterruptedException e1) {
            //e1.printStackTrace();
        }
    }

    public Event getEvent(){
        try {
            return eventQueue.take();
        } catch (InterruptedException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
