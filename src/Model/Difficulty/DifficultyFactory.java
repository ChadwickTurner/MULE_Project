package Model.Difficulty;

import main.Constants;

/**
 * Created by Nate on 12/3/2015.
 */
public class DifficultyFactory {
    public static Difficulty create(String type){
        switch(type){
            case Constants.BEGINNER_STR:
                return new Difficulty_Beginner();
            case Constants.STANDARD_STR:
                return new Difficulty_Standard();
            case Constants.TOURNAMENT_STR:
                return new Difficulty_Tournament();
            default: break;
        }
        return null;
    }
}
