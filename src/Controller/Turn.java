package Controller;

import Model.GameSettings;
import Model.Player;
import Model.Store;
import View.GUIController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import main.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Nathan on 9/30/2015.
 */
public class Turn implements Runnable {
    private Player player;
    private volatile boolean isRunning = true;
    private volatile long timeLeft;
    private BufferedImage townImage;

    private static final int CENTERX = 447;
    private static final int CENTERY = 500;
    private static final int MAXPUB = 250;
    private static final int MILLISECONDS = 1000;
    private static final int SMALLOFFSET = 10;
    private static final int BIGOFFSET = 20;
    private static final int AFTERSTOREMOVE = 20;


    public Turn(Player playerI, long time) {
        this.player = playerI;
        timeLeft = time;
        try {
            BufferedImage src =  ImageIO.read(new File("map.png"));
            townImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d= townImage.createGraphics();
            g2d.drawImage(src, 0, 0, null);
            g2d.dispose();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public final void run() {
        while (isRunning) {
            navigateTown();
            if (!isRunning) { break; }
            navigateMap();
        }
    }

    /**
     * Stops the Turn from running
     */
    public final void kill() {
        isRunning = false;
    }

    public final void updateTime(long time){
        timeLeft = time;
    }

    private void navigateTown() {
        GUIController.getInstance().setMode(GUIController.Mode.TOWN);
        Point2D playerCoords = new Point2D(CENTERX, CENTERY);
        GUIController.getInstance().townDrawPlayer(playerCoords);
        if (player.getCurrentMule() != null) {
            GUIController.getInstance().townSpriteToMule(player.getCurrentMule().getType());
        }
        Event e;
        do {
            e = GUIController.getInstance().getEvent();
            if (e instanceof KeyEvent) {
                Point2D next = Util.navigateInvertVertical(((KeyEvent) e), playerCoords,
                        Constants.TOWN_BUMPER_PX_WIDTH, // Top
                        Constants.TOWN_BUMPER_PX_WIDTH + Constants.TOWN_PX_HEIGHT - Constants.SPRITE_PX_SIZE - SMALLOFFSET, // Bottom
                        Constants.TOWN_BUMPER_PX_WIDTH - BIGOFFSET, // Left
                        Constants.TOWN_BUMPER_PX_WIDTH + Constants.TOWN_PX_WIDTH - Constants.SPRITE_PX_SIZE + SMALLOFFSET, // Right
                        Constants.PIXELS_PER_MOVEMENT);
                if (next != null && getTownLocation(next) != TownLoc.WALL) {
                    playerCoords = next;
                }
                GUIController.getInstance().townDrawPlayer(playerCoords);
                TownLoc location = getTownLocation(playerCoords);
                playerCoords = processLocation(location, playerCoords);
            }
        } while (isRunning && getTownLocation(playerCoords) != TownLoc.GATE);
    }

    private Point2D processLocation(TownLoc location, Point2D playerCoords){
        Point2D newCoords = playerCoords;
        if (location == TownLoc.PUB) {
            int money = gamble();
            player.changeMoney(money);
            GUIController.getInstance().alert("Pub", player.getName() + " has won $" + money + " gambling at the pub.");
            GUIController.getInstance().townUpdateBorderInfo();
            GUIController.getInstance().getEvent();
            kill();
        }
        if (location == TownLoc.STORE_FOOD) {
            storeFood();
            newCoords = new Point2D(playerCoords.getX(), playerCoords.getY() + AFTERSTOREMOVE);
            GUIController.getInstance().townDrawPlayer(playerCoords);
        }
        if (location == TownLoc.STORE_ENERGY) {
            storeEnergy();
            newCoords = new Point2D(playerCoords.getX(), playerCoords.getY() + AFTERSTOREMOVE);
            GUIController.getInstance().townDrawPlayer(playerCoords);
        }
        if (location == TownLoc.STORE_SMITHORE) {
            storeSmithore();
            newCoords = new Point2D(playerCoords.getX(), playerCoords.getY() + AFTERSTOREMOVE);
            GUIController.getInstance().townDrawPlayer(playerCoords);
        }
        if (location == TownLoc.STORE_CRYSTITE) {
            storeCrystite();
            newCoords = new Point2D(playerCoords.getX(), playerCoords.getY() + AFTERSTOREMOVE);
            GUIController.getInstance().townDrawPlayer(playerCoords);
        }
        if (location == TownLoc.STORE_MULE) {
            storeMule();
            newCoords = new Point2D(playerCoords.getX(), playerCoords.getY() + AFTERSTOREMOVE);
            GUIController.getInstance().townDrawPlayer(playerCoords);
        }
        return newCoords;
    }

    private void storeFood(){
        Event e;
        storeEntered(TownLoc.STORE_FOOD, Constants.STORE_PRICE_FOOD, Store.getInstance().getFoodQuantity(), player.getFood());
        do {
            e = GUIController.getInstance().getEvent();
        } while (! (e instanceof ActionEvent));
        if(e.getSource() instanceof Button) {
            if (((Button) e.getSource()).getText().equals("Buy")) {
                if(!Store.getInstance().purchaseFood(player, GUIController.getInstance().townGetBuyCount())){
                    GUIController.getInstance().townDisplayPurchaseFail();
                }
            } else if (((Button) e.getSource()).getText().equals("Sell")) {
                Store.getInstance().sellFood(player, GUIController.getInstance().townGetSellCount());
            }
            GUIController.getInstance().townUpdateBorderInfo();
        }
    }

    private void storeEnergy(){
        Event e;
        storeEntered(TownLoc.STORE_ENERGY, Constants.STORE_PRICE_ENERGY, Store.getInstance().getEnergyQuantity(), player.getEnergy());
        do {
            e = GUIController.getInstance().getEvent();
        } while (! (e instanceof ActionEvent));
        if(e.getSource() instanceof Button) {
            if (((Button) e.getSource()).getText().equals("Buy")) {
                if(!Store.getInstance().purchaseEnergy(player, GUIController.getInstance().townGetBuyCount())){
                    GUIController.getInstance().townDisplayPurchaseFail();
                }
            } else if (((Button) e.getSource()).getText().equals("Sell")) {
                Store.getInstance().sellEnergy(player, GUIController.getInstance().townGetSellCount());
            }
            GUIController.getInstance().townUpdateBorderInfo();
        }
    }

    private void storeSmithore(){
        Event e;
        storeEntered(TownLoc.STORE_SMITHORE, Constants.STORE_PRICE_SMITHORE, Store.getInstance().getSmithoreQuantity(), player.getSmithore());
        do {
            e = GUIController.getInstance().getEvent();
        } while (! (e instanceof ActionEvent));
        if(e.getSource() instanceof Button) {
            if (((Button) e.getSource()).getText().equals("Buy")) {
                if(!Store.getInstance().purchaseSmithore(player, GUIController.getInstance().townGetBuyCount())){
                    GUIController.getInstance().townDisplayPurchaseFail();
                }
            } else if (((Button) e.getSource()).getText().equals("Sell")) {
                Store.getInstance().sellSmithore(player, GUIController.getInstance().townGetSellCount());
            }
            GUIController.getInstance().townUpdateBorderInfo();
        }
    }

    private void storeCrystite(){
        Event e;
        storeEntered(TownLoc.STORE_CRYSTITE, Constants.STORE_PRICE_CRYTSTITE, Store.getInstance().getCrystiteQuantity(), player.getCrystite());
        do {
            e = GUIController.getInstance().getEvent();
        } while (! (e instanceof ActionEvent));
        if(e.getSource() instanceof Button) {
            if (((Button) e.getSource()).getText().equals("Buy")) {
                if(!Store.getInstance().purchaseCrystite(player, GUIController.getInstance().townGetBuyCount())){
                    GUIController.getInstance().townDisplayPurchaseFail();
                }
            } else if (((Button) e.getSource()).getText().equals("Sell")) {
                Store.getInstance().sellCrystite(player, GUIController.getInstance().townGetSellCount());
            }
            GUIController.getInstance().townUpdateBorderInfo();
        }
    }

    private void storeMule(){
        Event e;
        GUIController.getInstance().townDisplayPopup(player.getCurrentMule(), TownLoc.STORE_MULE);
        do {
            e = GUIController.getInstance().getEvent();
        } while (! (e instanceof ActionEvent));
        if(e.getSource() instanceof Button) {
            String type = ((Button) e.getSource()).getText();
            if (!type.equals("Cancel")) {
                if(! Store.getInstance().purchaseMule(player, type)){
                    GUIController.getInstance().townDisplayPurchaseFail();
                } else {
                    GUIController.getInstance().townSpriteToMule(type);
                }
            }
            GUIController.getInstance().townUpdateBorderInfo();
        }
    }


    private TownLoc getTownLocation(Point2D point) {
//        if(point.getY() < 196 && point.getX() > 97 && point.getX() < 163){
//            return TownLoc.STORE_CRYSTITE;
//        }
        int rgb = Constants.IMAGE_RGB_OFFSET + townImage.getRGB((int)point.getX(), (int)point.getY());
        return TownLoc.values()[rgb];
//        if ((point.getX() <= Constants.TOWN_BUMPER_PX_WIDTH - 10
//                || point.getX() >= Constants.TOWN_BUMPER_PX_WIDTH + Constants.TOWN_PX_WIDTH - Constants.SPRITE_PX_SIZE)
//                && point.getY() >= 330 && point.getY() < 440)
//            return TownLoc.GATE;
//        if (point.getY() >= Constants.TOWN_BUMPER_PX_WIDTH + Constants.TOWN_PX_HEIGHT - Constants.SPRITE_PX_SIZE - 20
//                && point.getX() > 400 && point.getX() < 520)
//            return TownLoc.GATE;
//        if (point.getY() < 330 && point.getY() > 240 && (point.getX() < 410 || point.getX() > 485))
//            return TownLoc.WALL;
//        if (point.getY() > 420 && point.getX() < 390)
//            return TownLoc.WALL;
//        if (point.getY() <= 145) {
//            if (point.getX() < 392 && point.getX() > 350)
//                return TownLoc.WALL;
//            if (point.getX() < 230 && point.getX() > 190)
//                return TownLoc.WALL;
//            if (point.getX() < 550 && point.getX() > 505)
//                return TownLoc.WALL;
//            if (point.getX() < 705 && point.getX() > 665)
//                return TownLoc.WALL;
//        }
//        if (point.getY() > 145 && point.getY() < 205) {
//            if (point.getX() < 415 && point.getX() > 325)
//                return TownLoc.WALL;
//            if(point.getX() < 255 && point.getX() > 165)
//                return TownLoc.WALL;
//            if (point.getX() < 575 && point.getX() > 485)
//                return TownLoc.WALL;
//            if (point.getX() < 730 && point.getX() > 640)
//                return TownLoc.WALL;
//            if (point.getX() < 95 || point.getX() > 800)
//                return TownLoc.WALL;
//        }
//        if (point.getY() <= 240 && (point.getX() < 75 || point.getX() > 820))
//            return TownLoc.WALL;
//        if (point.getY() < 465 && point.getY() > 420)  //assay office
//            if ((point.getX() < 385 && point.getX() > 235) || point.getX() < 160)
//                return TownLoc.WALL;
//        if (point.getY() > 420)  //pub walls (bottom side is at 505)
//            if ((point.getX() > 510 && point.getX() < 660) || point.getX() > 735)
//                return TownLoc.WALL;
//        if (point.getY() > 440 && point.getX() > 660 && point.getX() < 735)
//            return TownLoc.PUB;
//        if (point.getY() <= 195) {
//            if (point.getX() >= 97 && point.getX() <= 162)
//                return TownLoc.WALL;
//            if (point.getX() >= 257 && point.getX() <= 322)
//                return TownLoc.STORE_SMITHORE;
//            if (point.getX() >= 417 && point.getX() <= 482)
//                return TownLoc.STORE_MULE;
//            if (point.getX() >= 577 && point.getX() <= 637)
//                return TownLoc.STORE_ENERGY;
//            if (point.getX() >= 732 && point.getX() <= 797)
//                return TownLoc.STORE_FOOD;
//        }
//
//        return TownLoc.NONE;
    }

    private void navigateMap() {
        GUIController.getInstance().setMode(GUIController.Mode.MAP);
        Point2D cursor = new Point2D(2+2, 2);
        GUIController.getInstance().mapMovePlayer(cursor);
        Event e;
        do {
            e = GUIController.getInstance().getEvent();
            if (e instanceof KeyEvent) {
                cursor = Util.navigateInvertVertical(((KeyEvent) e), cursor,
                        0, GameSettings.getInstance().getMap().getHeight() - 1,
                        0, GameSettings.getInstance().getMap().getWidth() - 1, 1);
                GUIController.getInstance().mapMovePlayer(cursor);
                if (((KeyEvent) e).getCode().getName().equals("Enter") && (player.getCurrentMule() != null) &&
                        (!Objects.equals(GameSettings.getInstance().getMap().getLocation(cursor).getType(), "Town"))) {
                    if(!player.settleMule(GameSettings.getInstance().getMap().getLocation(cursor))){
                        GUIController.getInstance().alert("", "Mule Lost. Player does not own location");
                    } else {
                        GUIController.getInstance().mapUpdateTiles();
                        GUIController.getInstance().mapMovePlayer(cursor);
                    }
                }
            }


        } while (isRunning && ((e instanceof KeyEvent) && !(((KeyEvent) e).getCode().getName().equals("Enter") &&
                Objects.equals(GameSettings.getInstance().getMap().getLocation(cursor).getType(), "Town"))));
    }

    private int gamble(){
        Random rand = new Random();
        int round = GameSettings.getInstance().getRound();
        int time = (int) (timeLeft / MILLISECONDS);
        int roundBonus, timeBonus;
        try {
            roundBonus = Constants.PUB_ROUNDBONUS[round];
            timeBonus = Constants.PUB_TIMEBONUS[time];
        } catch(ArrayIndexOutOfBoundsException arrEx){
            return 0;
        }
        int money = rand.nextInt(timeBonus + 1) + roundBonus;
        if(money > MAXPUB){
            money = MAXPUB;
        }
        return money;
    }

    private void storeEntered(TownLoc location, int price, int maxBuy, int maxSell){
        GUIController.getInstance().townDisplayPopup(player.getCurrentMule(), location);
        GUIController.getInstance().townUpdateCost(price);
        GUIController.getInstance().townUpdateValue(price);
        GUIController.getInstance().townSetMaxBuyAmount(maxBuy);
        GUIController.getInstance().townSetMaxSellAmount(maxSell);
    }

    public enum TownLoc {
        NONE,
        WALL,
        GATE,
        PUB,
        STORE_MULE,
        STORE_FOOD,
        STORE_ENERGY,
        STORE_SMITHORE,
        STORE_CRYSTITE
    }
}
