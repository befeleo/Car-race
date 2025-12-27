package com.autonest;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(500, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        canvas.setFocusTraversable(true);
        canvas.requestFocus();

        Image playerImg = new Image(getClass().getResource("/images/car_self.png").toExternalForm());
        PlayerCar player = new PlayerCar(200, 400, playerImg); // inside road
        Game game = new Game(player);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    player.moveLeft();
                    player.setSprite(new Image(getClass().getResource("/images/car_left1.png").toExternalForm()));
                    break;
                case RIGHT:
                    player.moveRight();
                    player.setSprite(new Image(getClass().getResource("/images/car_right1.png").toExternalForm()));
                    break;
                case P: game.togglePause(); break;
                case R: game.restart(); break;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                case RIGHT:
                    player.stopHorizontal();
                    player.setSprite(new Image(getClass().getResource("/images/car_self.png").toExternalForm()));
                    break;
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.update();
                game.render(gc);
            }
        }.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Vertical Lane Racing Game");
        primaryStage.show();
        root.requestFocus();
    }

    public static void main(String[] args) { launch(args); }
}
