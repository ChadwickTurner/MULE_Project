package Model;

import Model.Difficulty.Difficulty;
import Model.Difficulty.DifficultyFactory;
import javafx.stage.Stage;
import main.Constants;

import java.util.ArrayList;

/**
 * Created by Nathan on 9/10/2015.
 */
public class GameSettings {
    private static GameSettings _gameSettings = new GameSettings(DifficultyFactory.create(Constants.BEGINNER_STR), "Default");
    private Difficulty difficulty;
    private String mapName;
    private Map map;
    private ArrayList<Player> players;
    private ArrayList<Player> playersOriginalOrder;
    private Stage stage;
    private Mode mode;
    private int current;
    private ArrayList<Player.Color> availableColors;
    private ArrayList<Player.Race> availableRaces;
    private int round;

    private GameSettings(Difficulty diff, String m) {
        difficulty = diff;
        mapName = m;
        players = new ArrayList<Player>();
        playersOriginalOrder = new ArrayList<Player>();
        current = 0;
        round = 1;

        availableColors = new ArrayList<Player.Color>();
        availableColors.add(Player.Color.Red);
        availableColors.add(Player.Color.Blue);
        availableColors.add(Player.Color.Green);
        availableColors.add(Player.Color.Purple);

        availableRaces = new ArrayList<Player.Race>();
        availableRaces.add(Player.Race.Humanoid);
        availableRaces.add(Player.Race.Mechtron);
        //availableRaces.add(Player.Race.Bonzoid);
       // availableRaces.add(Player.Race.Gollumer);
        availableRaces.add(Player.Race.Leggite);
        availableRaces.add(Player.Race.Flapper);
        //availableRaces.add(Player.Race.Spheroid);
        //availableRaces.add(Player.Race.Packer);
    }

    public void NewGame(int round){
        players = new ArrayList<>();
        playersOriginalOrder = new ArrayList<>();
        this.round = round;
    }

    public static GameSettings getInstance() {
        return _gameSettings;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void addPlayer(Player p) {
        players.add(p);
        playersOriginalOrder.add(p);
        availableColors.remove(p.getColor());
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getPlayersOriginalOrder() {
        return playersOriginalOrder;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ArrayList<Player.Color> getAvailableColors() {
        return availableColors;
    }

    public ArrayList<Player.Race> getAvailableRaces() {
        return availableRaces;
    }

    public Player getPlayer(int playerNumber) {
        return players.get(playerNumber);
    }

    public int getCurr() {
        return current;
    }

    public void setCurr(int i) {
        current = i;
    }

    public Player getCurrent() {
        return players.get(current);
    }

    public void produceAll() {
        for(Player p : players) {
            p.produceAllMules();
        }
    }

    public void reorderPlayers() {
        players.sort((p1, p2) -> (p1.getScore() - p2.getScore()));
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode == Mode.Turns) {
            current = 0;
        }
    }

    public boolean nextTurn() {
        return ++current < players.size();
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void newMap(){
        if(mapName.equals("Random")){
            this.map = new RandomMap(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
        } else {
            this.map = new Map(Constants.DEFAULT_MAP);
        }
    }

    public int getRound() {
        return round;
    }

    public void nextRound() {
        round++;
    }

    public enum Mode {
        LandGrab,
        Turns
    }
}
