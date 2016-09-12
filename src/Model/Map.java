package Model;

import Model.Locations.*;
import javafx.geometry.Point2D;
import main.Constants;

import java.util.Random;

/**
 * Created by Nathan on 9/17/2015.
 */
public class Map {
    private Location[][] locations;
    private int height;
    private int width;

    public Map(String[][] map){
        height = map.length;                                                                                                                                                                                                                                                                            
        width = map[0].length;
        locations = new Location[height][width];
        for(int i = 0; i< height; i++){
            for(int j=0;j<width;j++){
                locations[i][j] = LocationFactory.create(map[i][j]);
            }
        }
    }

    public Map(Location[][] map){
        height = map.length;
        width = map[0].length;
        locations = map.clone();
    }

    public final int getHeight() {
        return height;
    }

    public final int getWidth() {
        return width;
    }

    public final Location getLocation(int y, int x){
        return locations[y][x];
    }

    public final Location getLocation(Point2D point){
        return locations[((int) point.getY())][((int) point.getX())];
    }
}
