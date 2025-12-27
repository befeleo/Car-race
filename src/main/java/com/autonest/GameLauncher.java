package com.autonest;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    private Stage primaryStage;
    private int highScore = 20;
    private int currentScore = 14;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showWelcomeScreen();
    }

    public void showWelcomeScreen() {

        Label titleLabel = new Label("Welcome to Car Racing Game");
        titleLabel.setId("welcome-title");

        Button startButton = new Button("START");
        startButton.setId("start-button");

        Button exitButton = new Button("EXIT");
        exitButton.setId("exit-button");

        startButton.setOnAction(e -> showGameOverScreen());
        exitButton.setOnAction(e -> System.exit(0));

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setId("welcome-layout");
        layout.getChildren().addAll(titleLabel, startButton, exitButton);

        Scene scene = new Scene(layout, 700, 500);
        scene.getStylesheets().add(getClass().getResource("css/welcome.css").toExternalForm());

        primaryStage.setTitle("Car Racing Game - Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showGameOverScreen() {

        boolean isHighScore = false;

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setId("gameover-title");

        Label scoreLabel = new Label("Your Score: " + currentScore);
        scoreLabel.setId("score-label");

        Label highScoreLabel = new Label("High Score: " + highScore);
        highScoreLabel.setId("score-label");

        Label newHighScoreLabel = null;
        if (isHighScore) {
            newHighScoreLabel = new Label("NEW HIGH SCORE!");
            newHighScoreLabel.setId("score-label");
        }

        Button restartButton = new Button("RESTART");
        restartButton.setId("restart-button");

        Button exitButton = new Button("EXIT");
        exitButton.setId("gameover-exit-button");

        restartButton.setOnAction(e -> showWelcomeScreen());
        exitButton.setOnAction(e -> System.exit(0));

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setId("gameover-layout");

        layout.getChildren().add(gameOverLabel);

        if (newHighScoreLabel != null) {
            layout.getChildren().add(newHighScoreLabel);
        }

        layout.getChildren().add(scoreLabel);
        layout.getChildren().add(highScoreLabel);
        layout.getChildren().addAll(restartButton, exitButton);

        Scene scene = new Scene(layout, 700, 500);
        scene.getStylesheets().add(getClass().getResource("css/gameover.css").toExternalForm());

        if (isHighScore) {
            primaryStage.setTitle("Car Racing Game - NEW HIGH SCORE!");
        } else {
            primaryStage.setTitle("Car Racing Game - Game Over");
        }
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}