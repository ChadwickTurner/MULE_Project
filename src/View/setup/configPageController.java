package View.setup;

import Model.Difficulty.Difficulty;
import Model.Difficulty.DifficultyFactory;
import Model.GameSettings;
import Model.Player;
import View.GUIController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Chad on 9/9/2015.
 */
public class configPageController implements Initializable{

    @FXML private ChoiceBox<String> mapType;
    @FXML private ChoiceBox<String> diffLevel;
    @FXML private Label numPlayers;

    @FXML private Rectangle buttonBlur;
    @FXML private Button addPlayerButton;

    @FXML private Rectangle player1Rect;
    @FXML private Label player1Name;
    @FXML private Label player1Race;
    @FXML private ImageView player1Sprite;

    @FXML private Rectangle player2Rect;
    @FXML private Label player2Name;
    @FXML private Label player2Race;
    @FXML private ImageView player2Sprite;

    @FXML private Rectangle player3Rect;
    @FXML private Label player3Name;
    @FXML private Label player3Race;
    @FXML private ImageView player3Sprite;

    @FXML private Rectangle player4Rect;
    @FXML private Label player4Name;
    @FXML private Label player4Race;
    @FXML private ImageView player4Sprite;

    private static final double OPACITY = 0.72;



    @FXML
    private void handleButtonAction() throws IOException {
        updateSettings();
        GUIController gc = GUIController.getInstance();
        gc.setMode(GUIController.Mode.ADD_PLAYER);
    }

    @FXML
    private void handleSaveButtonAction() throws IOException {
        updateSettings();
        GUIController gc = GUIController.getInstance();
        gc.setMode(GUIController.Mode.INFO_PAGE);
    }

    /**
     * Makes sure the displays keep what the player already chose
     */
    private void updateSettings(){
        GameSettings.getInstance().setDifficulty(DifficultyFactory.create(diffLevel.getValue()));
        GameSettings.getInstance().setMapName(mapType.getValue());
    }

    @Override
    public final void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        diffLevel.setItems(FXCollections.observableArrayList(Constants.BEGINNER_STR, Constants.STANDARD_STR, Constants.TOURNAMENT_STR));
        diffLevel.setValue(GameSettings.getInstance().getDifficulty().getString());
        diffLevel.setTooltip(new Tooltip("Choose the difficulty level."));

        mapType.setItems(FXCollections.observableArrayList("Default", "Random"));
        mapType.setValue(GameSettings.getInstance().getMapName());
        mapType.setTooltip(new Tooltip("Choose the map."));

        numPlayers.setText(String.valueOf(GameSettings.getInstance().getPlayers().size()));

        int playerCnt = GameSettings.getInstance().getPlayers().size();

        if(playerCnt == 1) {
            displayPlayer1();
        } else if (playerCnt == 2) {
            displayPlayer1();
            displayPlayer2();
        } else if (playerCnt == 1+2) {
            displayPlayer1();
            displayPlayer2();
            displayPlayer3();
        } else if (playerCnt == 2+2) {
            displayPlayer1();
            displayPlayer2();
            displayPlayer3();
            displayPlayer4();

            addPlayerButton.getStylesheets().add("View/CSS/errorButton.css");
            addPlayerButton.getStylesheets().remove("View/CSS/button.css");
            buttonBlur.setOpacity(OPACITY);
            buttonBlur.toFront();
        }
    }

    /**
     * Colors the background of the player display based on the color they chose
     *
     * @param c the enum color they chose
     * @param r the display background
     */
    private void enumColor(Player.Color c, Rectangle r) {
        if (c == Player.Color.Blue){
            r.setFill(Color.BLUE);
        } else if (c == Player.Color.Red) {
            r.setFill(Color.RED);
        } else if (c == Player.Color.Green) {
            r.setFill(Color.GREEN);
        } else if (c == Player.Color.Purple) {
            r.setFill(Color.PURPLE);
        }
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(1+2);
    }

    /**
     * Displays the info for Player 1.
     */
    private void displayPlayer1() {
        Player p1 = GameSettings.getInstance().getPlayer(0);
        Player.Color color = p1.getColor();
        Player.Race r = p1.getRace();
        enumColor(color, player1Rect);
        player1Name.setText(p1.getName());
        player1Race.setText(p1.getRace().toString());
        String inFile = Constants.SPRITE_LOCATION;
        inFile += r.toString() + "/";
        inFile += color.toString().toLowerCase() + Constants.IMAGE_FILE_TYPE;
        player1Sprite.setImage(new Image(inFile));

        player1Rect.setOpacity(1);
        player1Name.setOpacity(1);
        player1Race.setOpacity(1);
        player1Sprite.setOpacity(1);
    }

    /**
     * Displays the info for Player 2.
     */
    private void displayPlayer2() {
        Player p2 = GameSettings.getInstance().getPlayer(1);
        Player.Color color = p2.getColor();
        Player.Race r = p2.getRace();
        enumColor(color, player2Rect);
        player2Name.setText(p2.getName());
        player2Race.setText(p2.getRace().toString());
        String inFile = Constants.SPRITE_LOCATION;
        inFile += r.toString() + "/";
        inFile += color.toString().toLowerCase() + Constants.IMAGE_FILE_TYPE;
        player2Sprite.setImage(new Image(inFile));

        player2Rect.setOpacity(1);
        player2Name.setOpacity(1);
        player2Race.setOpacity(1);
        player2Sprite.setOpacity(1);
    }

    /**
     * Displays the info for Player 3.
     */
    private void displayPlayer3() {
        Player p3 = GameSettings.getInstance().getPlayer(2);
        Player.Color color = p3.getColor();
        Player.Race r = p3.getRace();
        enumColor(color, player3Rect);
        player3Name.setText(p3.getName());
        player3Race.setText(p3.getRace().toString());
        String inFile = Constants.SPRITE_LOCATION;
        inFile += r.toString() + "/";
        inFile += color.toString().toLowerCase() + Constants.IMAGE_FILE_TYPE;
        player3Sprite.setImage(new Image(inFile));

        player3Rect.setOpacity(1);
        player3Name.setOpacity(1);
        player3Race.setOpacity(1);
        player3Sprite.setOpacity(1);
    }

    /**
     * Displays the info for Player 4.
     */
    private void displayPlayer4() {
        Player p4 = GameSettings.getInstance().getPlayer(1+2);
        Player.Color color = p4.getColor();
        Player.Race r = p4.getRace();
        enumColor(color, player4Rect);
        player4Name.setText(p4.getName());
        player4Race.setText(p4.getRace().toString());
        String inFile = Constants.SPRITE_LOCATION;
        inFile += r.toString() + "/";
        inFile += color.toString().toLowerCase() + Constants.IMAGE_FILE_TYPE;
        player4Sprite.setImage(new Image(inFile));

        player4Rect.setOpacity(1);
        player4Name.setOpacity(1);
        player4Race.setOpacity(1);
        player4Sprite.setOpacity(1);
    }
}
