package main;

import Controller.SettingsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Model.GameSettings;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    static Media kutmah;
    static MediaPlayer kutmahPlayer;
    static Media datass;
    static MediaPlayer datassPlayer;
    static Media curby;
    static MediaPlayer curbyPlayer;
    static Media velvet;
    static MediaPlayer velvetPlayer;
    static Media hella;
    static MediaPlayer hellaPlayer;
    static Media after;
    static MediaPlayer afterPlayer;

    @Override
    public final void start(Stage primaryStage) throws IOException{
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        playMusic();
        GameSettings.getInstance().setStage(primaryStage);
        new Thread(SettingsController.getInstance()).start();
    }

    private static void playMusic(){
        kutmah = new Media(new File("1forKutmah.mp3").toURI().toString());
        kutmahPlayer = new MediaPlayer(kutmah);
        kutmahPlayer.play();
        kutmahPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                kutmahPlayer.stop();
                datassPlayer.play();
            }
        });
        datass = new Media(new File("DatAss.mp3").toURI().toString());
        datassPlayer = new MediaPlayer(datass);
        datassPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                datassPlayer.stop();
                curbyPlayer.play();
            }
        });
        curby = new Media(new File("Curby.mp3").toURI().toString());
        curbyPlayer = new MediaPlayer(curby);
        curbyPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                curbyPlayer.stop();
                velvetPlayer.play();
            }
        });
        velvet = new Media(new File("ReadVelvet.mp3").toURI().toString());
        velvetPlayer = new MediaPlayer(velvet);
        velvetPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                velvetPlayer.stop();
                hellaPlayer.play();
            }
        });
        hella = new Media(new File("Hella-Copter.mp3").toURI().toString());
        hellaPlayer = new MediaPlayer(hella);
        hellaPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                hellaPlayer.stop();
                afterPlayer.play();
            }
        });
        after = new Media(new File("Afterparty.mp3").toURI().toString());
        afterPlayer = new MediaPlayer(after);
        afterPlayer.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                afterPlayer.stop();
                kutmahPlayer.play();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
