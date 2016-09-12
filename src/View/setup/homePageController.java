package View.setup;

import Controller.SettingsController;
import View.GUIController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import main.FileSaver;

import java.io.IOException;

/**
 * Created by Chad on 9/9/2015.
 */
public class homePageController {

    @FXML private ImageView newGameArr1;
    @FXML private ImageView newGameArr2;
    @FXML private ImageView contGameArr1;
    @FXML private ImageView contGameArr2;

    @FXML
    private void handleButtonAction() throws IOException {
        GUIController gc = GUIController.getInstance();
        gc.setMode(GUIController.Mode.CONFIG);
    }

    @FXML
    private void continueGame() {
        FileSaver.loadGame("save1");
        SettingsController.getInstance().kill();
    }

    @FXML
    private void mouseEnterNewGame() {
        newGameArr1.setOpacity(1);
        newGameArr2.setOpacity(1);
    }

    @FXML
    private void mouseExitNewGame() {
        newGameArr1.setOpacity(0);
        newGameArr2.setOpacity(0);
    }

    @FXML
    private void mouseEnterContGame() {
        contGameArr1.setOpacity(1);
        contGameArr2.setOpacity(1);
    }

    @FXML
    private void mouseExitContGame() {
        contGameArr1.setOpacity(0);
        contGameArr2.setOpacity(0);
    }
}
