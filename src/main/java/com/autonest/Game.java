package com.autonest;


// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private int score;
    private int highScore;
    private boolean isGameOver;
    private PlayerCar player;
    private List<RandomCar> randomCars;

    public Game() {
        score = 0;
        highScore = 0;
        isGameOver = false;
        randomCars = new ArrayList<>();
    }

    public void start() {

    }

    public void update() {
        if (isGameOver)
            return;

        spawnRandomCar();
        checkCollision();
        updateScore();

        for (RandomCar car : randomCars) {
            car.move();
        }
    }

    public void spawnRandomCar() {
        if (randomCars.size() < 4 && Math.random() < 0.02) {

        }
    }

    public void checkCollision() {

    }

    public void updateScore() {
        for (int i = 0; i < randomCars.size(); i++) {

        }
    }

    public void endGame() {
        isGameOver = true;

        String message = "Game Over! Score: " + score;
        if (score == highScore && score > 0) {
            message += "\nNew High Score!";
        }

        System.out.println(message);
    }

}