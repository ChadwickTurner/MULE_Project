package Controller;

import Model.Location;
import Model.Locations.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import main.Constants;

import java.util.Random;


import java.io.File;
import javax.sound.sampled.*;

/**
 * Created by Nate on 10/25/2015.
 */
public final class Util {

    private Util(){
        throw new AssertionError("Instantiating utility class...");
    }

    public static Point2D navigate(KeyEvent e, Point2D start, int top, int bottom, int left, int right, int dist){
        Point2D next = start;
        if (e.getCode().getName().equals("Down") && start.getY() >= bottom + dist) {
            next = new Point2D(start.getX(), start.getY() - dist);
        } else if (e.getCode().getName().equals("Up") && start.getY() <= top - dist) {
            next = new Point2D(start.getX(), start.getY() + dist);
        } else if (e.getCode().getName().equals("Right") && start.getX() <= right - dist) {
            next = new Point2D(start.getX() + dist, start.getY());
        } else if (e.getCode().getName().equals("Left") && start.getX() >= left + dist) {
            next = new Point2D(start.getX() - dist, start.getY());
        }
        return next;
    }

    public static Point2D navigateInvertVertical(KeyEvent e, Point2D start, int top, int bottom, int left, int right, int dist){
        Point2D next = start;
        if (e.getCode().getName().equals("Down") && start.getY() <= bottom - dist) {
            next = new Point2D(start.getX(), start.getY() + dist);
        } else if (e.getCode().getName().equals("Up") && start.getY() >= top + dist) {
            next = new Point2D(start.getX(), start.getY() - dist);
        } else if (e.getCode().getName().equals("Right") && start.getX() <= right - dist) {
            next = new Point2D(start.getX() + dist, start.getY());
        } else if (e.getCode().getName().equals("Left") && start.getX() >= left + dist) {
            next = new Point2D(start.getX() - dist, start.getY());
        }
        return next;
    }

    public static Location[][] getRandomLocs(int width, int height){
        Location[][] locations = new Location[height][width];
        Random rand = new Random();
        int townx = rand.nextInt(width);
        int towny = rand.nextInt(height);
        boolean riverVertical = true; //rand.nextBoolean(); cant draw horizontal rivers rn
        int riverIndex = riverVertical ? rand.nextInt(width) : rand.nextInt(height);

        for(int i = 0; i< height; i++){
            for(int j = 0; j< width; j++){
                Location loc;
                if(i == towny && j == townx){
                    loc = LocationFactory.create(Constants.TOWN_STR);
                } else if (j == riverIndex) {
                    loc = LocationFactory.create(Constants.RIVER_STR);
                } else { // randomly do either flatland or mountain
                    loc = LocationFactory.create(Constants.RAND_WEIGH_LOC_STRS[rand.nextInt(Constants.RAND_WEIGH_LOC_STRS.length)]);
                }
                locations[i][j] = loc;
            }
        }
        return locations;
    }
}
