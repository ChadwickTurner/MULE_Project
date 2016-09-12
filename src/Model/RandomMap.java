package Model;

import Controller.Util;

/**
 * Created by Nate on 12/3/2015.
 */
public class RandomMap extends Map {

    public RandomMap(int width, int height){
        super(Util.getRandomLocs(width, height));
    }


}
