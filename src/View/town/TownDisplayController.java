package View.town;

import Controller.Turn;
import Model.BorderInfo;
import Model.GameSettings;
import Model.Mule;
import Model.Player;
import View.Bordered;
import View.GUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.FileSaver;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Nate on 9/24/2015.
 */
public class TownDisplayController implements Initializable, Bordered {

//    @FXML
//    private AnchorPane mapAnchor;

    @FXML
    private ImageView playerSprite;

    @FXML
    private Group crystiteUnit;
    @FXML
    private Group smithoreUnit;
    @FXML
    private Group energyUnit;
    @FXML
    private Group foodUnit;
    @FXML
    private Group muleUnit;
    @FXML
    private Group muleUnitFail;

//    @FXML
//    private Group smithoreUpgrade;
//    @FXML
//    private Group energyUpgrade;
//    @FXML
//    private Group foodUpgrade;

    @FXML
    private Group buySellLayout;
    @FXML
    private Label unitBuyAmount;
    @FXML
    private Label unitCost;

    @FXML
    private Label unitSellAmount;
    @FXML
    private Label unitValue;

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

    @FXML
    private Group purchaseFail;

    private int cost;
    private int value;

    private int maxBuy;
    private int maxSell;

    private int buyCounter;
    private int sellCounter;

    private Group currentPopup;

    @Override
    public final  void initialize(URL location, ResourceBundle resources) {
        Player.Color c = GameSettings.getInstance().getCurrent().getColor();
        Player.Race r = GameSettings.getInstance().getCurrent().getRace();
        String inFile = "/View/images/Sprites/";
        inFile += r.toString() + "/";
        inFile += c.toString().toLowerCase() + ".png";
        playerSprite.setImage(new Image(inFile));
        updateBorderInfo();
    }

    public final void drawPlayerSprite(Point2D p) {
        playerSprite.setLayoutX(p.getX());
        playerSprite.setLayoutY(p.getY());
    }

    public final void spriteToMule(String type){
        Player.Color c = GameSettings.getInstance().getCurrent().getColor();
        Player.Race r = GameSettings.getInstance().getCurrent().getRace();
        String inFile = "/View/images/Sprites/";
        inFile += r.toString() + "/";
        inFile += "Mule/" + type + "/";
        inFile += c.toString().toLowerCase() + ".png";
        playerSprite.setImage(new Image(inFile));
    }

    @Override
    public final void updateTimer() {
        timerDisplay.setText(String.format("%02d", BorderInfo.getTimeLeft()));
    }

    @FXML
    private void incrementBuyCounter() {
        if (buyCounter != maxBuy) {
            buyCounter++;
        }
        unitBuyAmount.setText(buyCounter + "");
        updateCostLabel();
    }

    @FXML
    private void decrementBuyCounter() {
        if (buyCounter != 0) {
            buyCounter--;
        }
        unitBuyAmount.setText(buyCounter + "");
        updateCostLabel();
    }

    @FXML
    private void incrementSellCounter() {
        if (sellCounter != maxSell) {
            sellCounter++;
        }
        unitSellAmount.setText(sellCounter + "");
        updateValueLabel();
    }

    @FXML
    private void decrementSellCounter() {
        if (sellCounter != 0) {
            sellCounter--;
        }
        unitSellAmount.setText(sellCounter + "");
        updateValueLabel();
    }

    @FXML
    private void buyMule(ActionEvent event) {
        //Yes button on buy mule popup
        GUIController.getInstance().sendEvent(event);
        muleUnit.toBack();
    }

    @FXML
    private void cancelMule(ActionEvent event) {
        //No button on buy mule popup
        GUIController.getInstance().sendEvent(event);
        muleUnit.toBack();
        muleUnitFail.toBack();
    }

    @FXML
    private void buy(ActionEvent event) {
        GUIController.getInstance().sendEvent(event);
        currentPopup.toBack();
        buySellLayout.toBack();
    }

    @FXML
    private void sell(ActionEvent event) {
        // sell button on all other units
        GUIController.getInstance().sendEvent(event);
        currentPopup.toBack();
        buySellLayout.toBack();
    }

    @FXML
    private void upgradeSmithore(ActionEvent event) {
        // Upgrade button in Smithore popup
        GUIController.getInstance().sendEvent(event);
        currentPopup.toBack();
        buySellLayout.toBack();
    }

    @FXML
    private void upgradeFood(ActionEvent event) {
        GUIController.getInstance().sendEvent(event);
        currentPopup.toBack();
        buySellLayout.toBack();
    }

    @FXML
    private void upgradeEnergy(ActionEvent event) {
        // Upgrade button in Energy upgrade popup
        GUIController.getInstance().sendEvent(event);
        currentPopup.toBack();
        buySellLayout.toBack();
    }

    public final int getBuyCount() {
        return Integer.parseInt(unitBuyAmount.getText());
    }

    public final int getSellCount() {
        return Integer.parseInt(unitSellAmount.getText());
    }

    public final void updateCost(int costI) {
        this.cost = costI;
        buyCounter = 0;
        unitBuyAmount.setText("0");
        updateCostLabel();
    }

    public final void updateValue(int valueI) {
        this.value = valueI;
        sellCounter = 0;
        unitSellAmount.setText("0");
        updateValueLabel();
    }

    public final void setMaxBuyAmount(int amount) {
        maxBuy = amount;
    }

    public final void setMaxSellAmount(int amount) {
        maxSell = amount;
    }

    private void updateCostLabel() {
        int total = this.getBuyCount() * cost;

        unitCost.setText(total + "");
    }

    private void updateValueLabel() {
        int total = this.getSellCount() * value;

        unitValue.setText(total + "");
    }

    public final void removePopup() {
        if (currentPopup != null) {
            currentPopup.toBack();
        }
        buySellLayout.toBack();
    }

    public final void displayPurchaseFail() {
        currentPopup = purchaseFail;
        currentPopup.toFront();
    }

    public final void displayPopup(Mule mule, Turn.TownLoc loc) {
        boolean hasMule = (mule != null);
        switch (loc) {
            case STORE_CRYSTITE:
                currentPopup = crystiteUnit;
                currentPopup.toFront();
                buySellLayout.toFront();
                break;
            case STORE_SMITHORE:
                currentPopup = smithoreUnit;
                currentPopup.toFront();
                buySellLayout.toFront();
                break;
            case STORE_ENERGY:
                currentPopup = energyUnit;
                currentPopup.toFront();
                buySellLayout.toFront();
                break;
            case STORE_FOOD:
                currentPopup = foodUnit;
                currentPopup.toFront();
                buySellLayout.toFront();
                break;
            case STORE_MULE:
                if (hasMule) {
                    currentPopup = muleUnitFail;
                } else {
                    currentPopup = muleUnit;
                }
                currentPopup.toFront();
                break;
        }
    }

    @FXML
    public final void save(ActionEvent e) {
        FileSaver.saveGame("save1");
        GUIController.getInstance().alert("", "Game saved!");
    }

    public final void updateBorderInfo() {
        stateDisplay.setText(BorderInfo.getMode());
        playerBannerDisplay.setText(BorderInfo.getCurrentPlayer().getPlayerName());
        playerDisplay.setText(BorderInfo.getCurrentPlayer().getPlayerName());
        Player.Color col = BorderInfo.getCurrentPlayer().getPlayerColor();
        Player.Race r = GameSettings.getInstance().getCurrent().getRace();
        String inFile = "/View/images/Sprites/";
        inFile += r.toString() + "/";
        inFile += col.toString().toLowerCase() + ".png";
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
