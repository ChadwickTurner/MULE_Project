package main;

import Controller.MapController;
import Controller.TurnController;
import Model.*;
import Model.Locations.LocationFactory;
import Model.Mules.MuleFactory;

import java.io.*;
import java.util.Scanner;

/**
 * Created by weisk on 11/2/2015.
 */
public final class FileSaver {

    private FileSaver() {
        throw new AssertionError("Instantiating utility class...");
    }

    public static void saveGame(String filename) {
        BufferedWriter writer = null;
        File mapFile = new File("Saves\\" + filename + ".txt");
        try {
            writer = new BufferedWriter(new FileWriter(mapFile));
            writer.write("GAMESETTINGS");
            writer.newLine();
            writer.write("Round ");
            writer.write(GameSettings.getInstance().getRound() + "");
            writer.newLine();
//            writer.write(GameSettings.getInstance().getDifficulty().toString());
//            writer.newLine();
//            writer.write(GameSettings.getInstance().getMapName());
//            writer.newLine();
            writer.write("Current ");
            writer.write(GameSettings.getInstance().getCurr() + "");
            writer.newLine();
            writer.newLine();
            writer.write("PLAYERS");
            writer.newLine();
            for (Player p : GameSettings.getInstance().getPlayers()) {
                writer.write("PName ");
                writer.write(p.getName());
                writer.newLine();
                writer.write("Color ");
                writer.write(p.getColor().toString());
                writer.newLine();
                writer.write("Race ");
                writer.write(p.getRace().toString());
                writer.newLine();
                writer.write("Money ");
                writer.write(p.getMoney() + "");
                writer.newLine();
                writer.write("Food ");
                writer.write(p.getFood() + "");
                writer.newLine();
                writer.write("Energy ");
                writer.write(p.getEnergy() + "");
                writer.newLine();
                writer.write("Smithore ");
                writer.write(p.getSmithore() + "");
                writer.newLine();
                if (p.getCurrentMule() != null) {
                    writer.write("CurrentMule ");
                    writer.write(p.getCurrentMule().getType());
                    writer.newLine();
                }
            }
            Map map = GameSettings.getInstance().getMap();
            writer.newLine();
            writer.write("MAP");
            writer.newLine();
            writer.write("Width");
            writer.newLine();
            writer.write(map.getWidth() + "");
            writer.newLine();
            writer.write("Height");
            writer.newLine();
            writer.write(map.getHeight() + "");
            writer.newLine();
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    writer.write("Type ");
                    writer.write(map.getLocation(i, j).getType());
                    writer.newLine();
                    if (map.getLocation(i, j).getOwner() != null) {
                        writer.write("Owner ");
                        writer.write(GameSettings.getInstance().getPlayers().indexOf(map.getLocation(i, j).getOwner()) + "");
                        writer.newLine();
                        if (map.getLocation(i, j).getMule() != null) {
                            writer.write("Mule ");
                            writer.write(map.getLocation(i, j).getMule().getType());
                            writer.newLine();
                        }
                    }
                }
            }
            writer.newLine();
            writer.write("STORE");
            writer.newLine();
            writer.write("FoodQuantity ");
            writer.write(Store.getInstance().getFoodQuantity() + "");
            writer.newLine();
            writer.write("EnergyQuantity ");
            writer.write(Store.getInstance().getEnergyQuantity() + "");
            writer.newLine();
            writer.write("SmithoreQuantity ");
            writer.write(Store.getInstance().getSmithoreQuantity() + "");
            writer.newLine();
            writer.write("MuleQuantity ");
            writer.write(Store.getInstance().getMuleQuantity() + "");
            writer.newLine();
            writer.write("GameMode ");
            writer.write(GameSettings.getInstance().getMode().toString());
            if (GameSettings.getInstance().getMode() == GameSettings.Mode.Turns) {
                writer.newLine();
                writer.write("Time ");
                writer.write(TurnController.getInstance().getTime() + "");
            }
            try {
                writer.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    public static void loadGame(String filename) {
        InputStream inStream = null;
        try {
            inStream = new FileInputStream("Saves\\" + filename + ".txt");
            Scanner reader = new Scanner(inStream);
            reader.next();
            reader.next("Round");
            int round = reader.nextInt();
            reader.next("Current");
            int current = reader.nextInt();
            reader.next("PLAYERS");
            GameSettings.getInstance().NewGame(round);
            while (reader.hasNext("PName")) {
                reader.next("PName");
                String name = reader.next();
                reader.next("Color");
                Player.Color color = Player.Color.valueOf(reader.next());
                reader.next("Race");
                Player.Race race = Player.Race.valueOf(reader.next());
                reader.next("Money");
                int money = reader.nextInt();
                reader.next("Food");
                int food = reader.nextInt();
                reader.next("Energy");
                int energy = reader.nextInt();
                reader.next("Smithore");
                int smithore = reader.nextInt();
                Player p = new Player(name, color, race, money, food, energy, smithore);
                GameSettings.getInstance().addPlayer(p);
                if (reader.hasNext("CurrentMule")) {
                    reader.next();
                    p.setCurrentMule(MuleFactory.createMule(p, reader.next()));
                }

            }
            reader.next("MAP");
            reader.next("Width");
            int width = reader.nextInt();
            reader.next("Height");
            int height = reader.nextInt();
            Location[][] locations = new Location[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    reader.next("Type");
                    locations[i][j] = LocationFactory.create(reader.next());
                    if (reader.hasNext("Owner")) {
                        reader.next();
                        Player p = GameSettings.getInstance().getPlayers().get(reader.nextInt());
                        locations[i][j].setOwner(p);
                        if (reader.hasNext("Mule")) {
                            reader.next();
                            Mule m = MuleFactory.createMule(p, reader.next());
                            p.addMule(m);
                            m.setLocation(locations[i][j]);
                            locations[i][j].setMule(m);
                        }
                    }
                }
            }
            MapController.getInstance();
            GameSettings.getInstance().setMap(new Map(locations));

            reader.next("STORE");
            reader.next("FoodQuantity");
            int food = reader.nextInt();
            reader.next("EnergyQuantity");
            int energy = reader.nextInt();
            reader.next("SmithoreQuantity");
            int smithore = reader.nextInt();
            reader.next("MuleQuantity");
            int mule = reader.nextInt();
            Store.getInstance().newGame(food, energy, smithore, 0, mule);
            reader.next("GameMode");
            String mode = reader.next();
            if (mode.equals("LandGrab")) {
                GameSettings.getInstance().setMode(GameSettings.Mode.LandGrab);
                GameSettings.getInstance().setCurr(current);
                new Thread(MapController.getInstance()).start();
            } else if (mode.equals("Turns")) {
                reader.next("Time");
                int time = reader.nextInt();
                GameSettings.getInstance().setMode(GameSettings.Mode.Turns);
                GameSettings.getInstance().setCurr(current);
                TurnController.getInstance().startMid(time);
                new Thread(TurnController.getInstance()).start();
            }
        } catch (Exception e) {
        }

    }

}
