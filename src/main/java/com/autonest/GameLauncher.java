package com.autonest;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLauncher extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showWelcomeScreen();
    }

    // 🔹 Welcome Screen
    private void showWelcomeScreen() {

        Label titleLabel = new Label("Welcome to Car Racing Game");
        titleLabel.setId("welcome-title");

        Button startButton = new Button("START");
        startButton.setId("start-button");

        Button exitButton = new Button("EXIT");
        exitButton.setId("exit-button");

        startButton.setOnAction(e -> startGame());
        exitButton.setOnAction(e -> System.exit(0));

        VBox layout = new VBox(30, titleLabel, startButton, exitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setId("welcome-layout");

        Scene scene = new Scene(layout, 700, 700);
        scene.getStylesheets().add(getClass().getResource("/css/welcome.css").toExternalForm());

        primaryStage.setTitle("Car Racing Game - Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 🔹 Start Game
    private void startGame() {
        Game Game = new Game();
        Scene gameScene = new Scene(Game, 700, 700);
        Game.bindControls(gameScene);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Car Racing Game");

        // Start game loop
        Game.startGame();

        // Detect game over
        new Thread(() -> {
            while (!Game.isGameOver()) {
                try {
                    Thread.sleep(50);
                } catch (Exception ignored) {
                }
            }

            PauseTransition pause = new PauseTransition(Duration.seconds(1)); // 1 second boom
            pause.setOnFinished(ev -> showGameOverScreen(Game.getScore(), Game.getHighScore()));
            pause.play();
        }).start();
    }

    // 🔹 Game Over Screen
    private void showGameOverScreen(int currentScore, int highScore) {
        if (currentScore > highScore)
            highScore = currentScore;
        boolean isHighScore = currentScore == highScore && currentScore > 0;

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setId("gameover-title");

        Label scoreLabel = new Label("Your Score: " + currentScore);
        scoreLabel.setId("score-label");

        Label highScoreLabel = new Label("High Score: " + highScore);
        highScoreLabel.setId("score-label");

        VBox layout = new VBox(20, gameOverLabel, scoreLabel, highScoreLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setId("gameover-layout");

        if (isHighScore) {
            Label newHighLabel = new Label("NEW HIGH SCORE!");
            newHighLabel.setId("highscore-celebration");
            layout.getChildren().add(1, newHighLabel);
        }

        Button restartButton = new Button("RESTART");
        restartButton.setId("restart-button");
        restartButton.setOnAction(e -> startGame());

        Button exitButton = new Button("EXIT");
        exitButton.setId("gameover-exit-button");
        exitButton.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(restartButton, exitButton);

        Scene scene = new Scene(layout, 700, 700);
        scene.getStylesheets().add(getClass().getResource("/css/gameover.css").toExternalForm());

        primaryStage.setTitle(isHighScore ? "Car Racing Game - NEW HIGH SCORE!" : "Car Racing Game - Game Over");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
