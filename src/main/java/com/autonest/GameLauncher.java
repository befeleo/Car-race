package com.autonest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    @Override
    public void start(Stage stage) {
        Game world = new Game();

        Scene scene = new Scene(world, 500, 500);
        world.bindControls(scene);

        stage.setTitle("Car Racing Game (JavaFX)");
        stage.setScene(scene);
        stage.show();

        world.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
