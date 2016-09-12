package View.setup;

import Model.GameSettings;
import Model.Player;
import View.GUIController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Chad on 9/9/2015.
 */
public class addPlayerPageController implements Initializable{

    private static final int MAX_NAME_LENGTH = 8;

    @FXML private ChoiceBox<Player.Color> playerColor;
    @FXML private ChoiceBox<Player.Race> playerRace;
    @FXML private TextField playerName;

    @FXML private Rectangle buttonBlur;
    @FXML private Button addPlayerButton;

    @FXML
    private void handleButtonAction() throws IOException {
        GameSettings.getInstance().addPlayer(new Player(playerName.getText(), playerColor.getValue(), playerRace.getValue()));
        GUIController gc = GUIController.getInstance();
        gc.setMode(GUIController.Mode.CONFIG);
    }

    @Override
    public final void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        playerColor.setItems(FXCollections.observableArrayList(GameSettings.getInstance().getAvailableColors()));
        playerRace.setItems(FXCollections.observableArrayList(GameSettings.getInstance().getAvailableRaces()));
        final ArrayList<Boolean> bools = new ArrayList<Boolean>(3);
        bools.add(0, false);
        bools.add(1, false);
        bools.add(2, false);

        //Checking to make sure all three are filled before the player is created
        //Checking name
        playerName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                //implements max length for the name
                if (playerName.getText().length() > MAX_NAME_LENGTH) {
                    String s = playerName.getText().substring(0, MAX_NAME_LENGTH);
                    playerName.setText(s);
                }

                if (newValue.length() > 0) {
                    bools.set(0, true);
                    testAllEntries(bools);
                } else {
                    bools.set(0, false);
                }
            }
        });

        //Checking color
        playerColor.valueProperty().addListener(new ChangeListener<Player.Color>() {
            @Override
            public void changed(ObservableValue<? extends Player.Color> observable, Player.Color oldValue, Player.Color newValue) {
                if (newValue != null) {
                    bools.set(1, true);
                    testAllEntries(bools);
                } else {
                    bools.set(1, false);
                }
            }
        });

        //Checking race
        playerRace.valueProperty().addListener(new ChangeListener<Player.Race>() {
            @Override
            public void changed(ObservableValue<? extends Player.Race> observable, Player.Race oldValue, Player.Race newValue) {
                if (newValue != null) {
                    bools.set(2, true);
                    testAllEntries(bools);
                } else {
                    bools.set(2, false);
                }
            }
        });
    }

    /**
     * Verifies that all three values are filled before allowing new player creation
     * @param bools the list with the values of whether or not the values are filled
     */
    private void testAllEntries(List<Boolean> bools) {
        if (bools.get(0) && bools.get(1) && bools.get(2)) {
            addPlayerButton.getStylesheets().add("View/CSS/button.css");
            addPlayerButton.getStylesheets().remove("View/CSS/errorButton.css");
            buttonBlur.toBack();
            buttonBlur.setOpacity(0);
        }
    }
}
