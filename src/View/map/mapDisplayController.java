package View.map;

import View.Bordered;
import View.GUIController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import main.Constants;
import Model.BorderInfo;
import Model.GameSettings;
import Model.Map;
import Model.Mule;
import Model.Player;
import main.FileSaver;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Nate on 9/21/2015.
 */
public class mapDisplayController implements Initializable, Bordered {

    @FXML private Label display;
    @FXML private GridPane mapGrid;


    @FXML private Label stateDisplay;
    @FXML private Label playerBannerDisplay;
    @FXML private Label playerDisplay;
    @FXML private Label playerMoneyDisplay;
    @FXML private ImageView playerIconDisplay;
    @FXML private Label timerDisplay;
    @FXML private Label foodDisplay;
    @FXML private Label energyDisplay;
    @FXML private Label smithoreDisplay;
    @FXML private Label crystiteDisplay;
    @FXML private Group playerStatusDisplays;
    @FXML private Group storeStatusDisplay;
    @FXML private Button saveButton;

    private Boolean cursorChange = false;

    private static final int GRIDWIDTH = 9;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        ObservableList<Node> grid = mapGrid.getChildren();
        Map map = GameSettings.getInstance().getMap();
        for (int i = 0; i < Constants.MAP_HEIGHT; i++) {
            for (int j = 0; j < Constants.MAP_WIDTH; j++) {
                String imFile = "View/images/map/";
                imFile += map.getLocation(i, j).getShortType() + Constants.IMAGE_FILE_TYPE;

                ((ImageView)grid.get(i* GRIDWIDTH + j)).setImage(new Image(imFile));
                addOwnedBorders(new Point2D(j, i), 0);
            }
        }
        updateBorderInfo();
    }

    public final void setCursor(Point2D c){
        if (cursorChange) {
            mapGrid.getChildren().remove(mapGrid.getChildren().size() - 1);
        }
        mapGrid.add(new ImageView(new Image("/View/images/Borders/blackBorder.png")), (int) c.getX(), (int) c.getY());
        cursorChange = true;

    }

    public final void setPlayer(Point2D c){
        if (cursorChange) {
            mapGrid.getChildren().remove(mapGrid.getChildren().size() - 1);
        }
        Player p = GameSettings.getInstance().getCurrent();
        Player.Color col = p.getColor();
        Player.Race r = GameSettings.getInstance().getCurrent().getRace();
        Mule m = p.getCurrentMule();
        String inFile = "/View/images/Sprites/";
        inFile += r.toString() + "/";

        if (m != null) {
            inFile += "Mule/" + m.getType() + "/";
        }
        inFile += col.toString().toLowerCase() + Constants.IMAGE_FILE_TYPE;

        mapGrid.add(new ImageView(new Image(inFile)), (int) c.getX(), (int) c.getY());
        cursorChange = true;

    }

    public final void setDisplay(String d){
        display.setText(d);
    }

    public final void redrawTile(Point2D p){
        mapGrid.getChildren().remove(mapGrid.getChildren().size() - 1);
        Player.Color c = GameSettings.getInstance().getMap().getLocation(p).getOwner().getColor();
        String inFile = "/View/images/Borders/";
        inFile += c.toString().toLowerCase() + "Border.png";
        mapGrid.add(new ImageView(new Image(inFile)), (int)p.getX(), (int)p.getY());
        cursorChange = false;
    }

    public final void updateBorders() {
        ObservableList<Node> grid = mapGrid.getChildren();
        Map map = GameSettings.getInstance().getMap();
        int counter = 0;

        for (int i = 0; i < Constants.MAP_HEIGHT; i++) {
            for (int j = 0; j < Constants.MAP_WIDTH; j++) {
                String imFile = "View/images/map/";
                imFile += map.getLocation(i, j).getShortType() + Constants.IMAGE_FILE_TYPE;
                ((ImageView)grid.get(i* GRIDWIDTH + j)).setImage(new Image(imFile));
                counter = addOwnedBorders(new Point2D(j, i), counter);
            }
        }
        cursorChange = false;
        mapGrid.getChildren().remove(mapGrid.getChildren().size() - counter - 1);
    }

    private int addOwnedBorders(Point2D p, int i) {
        int counter = i;
        if (GameSettings.getInstance().getMap().getLocation(p).getOwner() != null) {
            Mule m = GameSettings.getInstance().getMap().getLocation(p).getMule();
            Player.Color c = GameSettings.getInstance().getMap().getLocation(p).getOwner().getColor();
            String inFile = "/View/images/Borders/";
            if (m != null) {
                inFile += m.getType() + "/";
            }
            inFile += c.toString().toLowerCase() + "Border.png";
            mapGrid.add(new ImageView(new Image(inFile)), (int) p.getX(), (int) p.getY());
            counter++;
        }
        return counter;
    }

    @FXML
    public final void save(ActionEvent e) {
        FileSaver.saveGame("save1");
        GUIController.getInstance().alert("", "Game saved!");
    }

    @Override
    public final void updateTimer() {
        timerDisplay.setText(String.format("%02d", BorderInfo.getTimeLeft()));
    }

    public final void updateBorderInfo() {
        stateDisplay.setText(BorderInfo.getMode());
        if (BorderInfo.getMode().equals("Land Grab")) {
            saveButton.toFront();
        } else {
            saveButton.toBack();
        }
        playerBannerDisplay.setText(BorderInfo.getCurrentPlayer().getPlayerName());
        playerDisplay.setText(BorderInfo.getCurrentPlayer().getPlayerName());
        Player.Color col = BorderInfo.getCurrentPlayer().getPlayerColor();
        String inFile = "/View/images/Sprites/Humanoid/";
        inFile += col.toString().toLowerCase() + Constants.IMAGE_FILE_TYPE;
        playerIconDisplay.setImage(new Image(inFile));
        playerMoneyDisplay.setText("$" + BorderInfo.getCurrentPlayer().getMoney());
        foodDisplay.setText("" + BorderInfo.getCurrentPlayer().getFood());
        energyDisplay.setText("" + BorderInfo.getCurrentPlayer().getEnergy());
        smithoreDisplay.setText("" + BorderInfo.getCurrentPlayer().getSmithore());
        crystiteDisplay.setText("" + BorderInfo.getCurrentPlayer().getCrystite());
        timerDisplay.setText(String.format("%02d", BorderInfo.getTimeLeft()));

        Group statusDisplay;
        for (int i = 0; i < BorderInfo.getNumPlayers(); i++) {
            statusDisplay = (Group) (playerStatusDisplays.getChildren().get(i));
            ((Label) statusDisplay.getChildren().get(0)).setText(BorderInfo.getPlayerInfo(i).getPlayerName());
            ((Label) statusDisplay.getChildren().get(1)).setText("$" + BorderInfo.getPlayerInfo(i).getMoney());
            ((Label) statusDisplay.getChildren().get(2)).setText("" + BorderInfo.getPlayerInfo(i).getFood());
            ((Label) statusDisplay.getChildren().get(1+2)).setText("" + BorderInfo.getPlayerInfo(i).getEnergy());
            ((Label) statusDisplay.getChildren().get(2+2)).setText("" + BorderInfo.getPlayerInfo(i).getSmithore());
            ((Label) statusDisplay.getChildren().get(2+2+1)).setText("" + BorderInfo.getPlayerInfo(i).getCrystite());
        }
        ((Label) storeStatusDisplay.getChildren().get(0)).setText("" + BorderInfo.getStoreInfo().getMules());
        ((Label) storeStatusDisplay.getChildren().get(1)).setText("" + BorderInfo.getStoreInfo().getFood());
        ((Label) storeStatusDisplay.getChildren().get(2)).setText("" + BorderInfo.getStoreInfo().getEnergy());
        ((Label) storeStatusDisplay.getChildren().get(1+2)).setText("" + BorderInfo.getStoreInfo().getSmithore());
        ((Label) storeStatusDisplay.getChildren().get(2+2)).setText("" + BorderInfo.getStoreInfo().getCrystite());
    }
}
