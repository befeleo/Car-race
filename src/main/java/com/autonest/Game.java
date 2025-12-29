package com.autonest;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game extends Pane {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private static final String HIGHSCORE_FILE = "highscore.txt";
    private int highScore = 0;

    private final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private final PlayerCar player = new PlayerCar();
    private final List<RandomCar> enemies = new ArrayList<>();
    private final Random random = new Random();

    private long lastSpawnTime = 0;
    private static long SPAWN_COOLDOWN = 1_500_000_000L;
    private static final double SAFE_SPAWN_Y = 220;
    private double currentEnemySpeed = 5.0;
    private static final double SPEED_INCREMENT = 0.2;

    private boolean gameOver = false;
    private int score = 0;
    private boolean showBoom = false;
    private double boomX, boomY;
    private long boomStartTime = 0;
    private final long BOOM_DURATION = 1_000_000_000L;

    private final int TOTAL_ROADS = 5;
    private double[] roadY = new double[TOTAL_ROADS];
    private static double ROAD_SPEED = 3;

    private static final double[] LANES = {
            190, 240, 290, 340, 390, 440
    };

    private double controlEasySpeed = 4;
    private double controlMediumSpeed = 5;
    private double controlHardSpeed = 6;
    private double controlExtremeSpeed = 8;

    public Game() {
        getChildren().add(canvas);
        this.highScore = loadHighScore();

        for (int i = 0; i < TOTAL_ROADS; i++) {
            roadY[i] = -i * HEIGHT;
        }
    }

    public void bindControls(Scene scene) {

        double controlSpeed;
        if (score > 35)
            controlSpeed = controlExtremeSpeed;
        else if (score > 25)
            controlSpeed = controlHardSpeed;
        else if (score > 15)
            controlSpeed = controlMediumSpeed;
        else
            controlSpeed = controlEasySpeed;

        scene.setOnKeyPressed(e -> {
            if (gameOver || showBoom)
                return;

            switch (e.getCode()) {
                case LEFT -> player.speedX = -controlSpeed;
                case RIGHT -> player.speedX = controlSpeed;
                case UP -> player.speedY = -controlSpeed + 1;
                case DOWN -> player.speedY = controlSpeed;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT, RIGHT -> player.speedX = 0;
                case UP -> player.speedY = isGameOver() ? 0 : 1;
                case DOWN -> player.speedY = 0;
            }
        });
    }

    public void startGame() {

        gameOver = false;
        showBoom = false;
        ROAD_SPEED = 3;
        player.speedX = 0;
        player.speedY = 1;
        lastSpawnTime = 0;
        SPAWN_COOLDOWN = 1_500_000_000L;
        enemies.forEach(e -> e.speed = 2);

        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (gameOver)
                    return;

                updateRoad();
                player.update();
                updateEnemies();
                spawnEnemies(now);
                checkCollision();
                render();
            }

        }.start();
    }

    private void updateRoad() {
        for (int i = 0; i < TOTAL_ROADS; i++) {
            roadY[i] += ROAD_SPEED;

            if (roadY[i] >= HEIGHT) {
                int topIndex = (i == 0) ? TOTAL_ROADS - 1 : i - 1;
                roadY[i] = roadY[topIndex] - (HEIGHT - 3);
            }
        }
    }

    private void updateEnemies() {
        if (showBoom || gameOver)
            return;
        enemies.forEach(RandomCar::update);

        enemies.removeIf(e -> {
            if (e.y > HEIGHT + 100) {
                score++;

                if (currentEnemySpeed < 10.0) {
                    currentEnemySpeed += SPEED_INCREMENT;
                    SPAWN_COOLDOWN = Math.max(400_000_000L, SPAWN_COOLDOWN - 100_000_000L);
                }

                return true;
            }
            return false;
        });
    }

    private void spawnEnemies(long now) {
        if (gameOver || showBoom)
            return;
        if (enemies.size() > 4 || now - lastSpawnTime < SPAWN_COOLDOWN)
            return;

        double lane = LANES[random.nextInt(LANES.length)];

        boolean laneBusy = false;
        for (RandomCar enemy : enemies) {

            if (Math.abs(enemy.x - lane) < 10 && enemy.y < SAFE_SPAWN_Y) {
                laneBusy = true;
                break;
            }

            if (enemy.y < 120) {
                laneBusy = true;
                break;
            }
        }

        if (!laneBusy) {
            String image = "car_" + (random.nextInt(6) + 1) + ".png";

            enemies.add(new RandomCar(lane, image, currentEnemySpeed));
            lastSpawnTime = now;
        }

    }

    private void checkCollision() {
        for (RandomCar enemy : enemies) {
            if (player.collidesWith(enemy) && !showBoom) {
                boomX = player.getCollisionX(enemy) - 37;
                boomY = player.getCollisionY(enemy) - 37;
                showBoom = true;
                boomStartTime = System.nanoTime();

                player.speedX = 0;
                player.speedY = 0;
                ROAD_SPEED = 0;
                enemies.forEach(e -> e.speed = 0);
                break;
            }
        }
        if (score > highScore) {
            highScore = score;
            saveHighScore(highScore);
        }

        if (showBoom && System.nanoTime() - boomStartTime > BOOM_DURATION) {
            gameOver = true;
        }

    }

    private void saveHighScore(int newScore) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGHSCORE_FILE))) {
            writer.println(newScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int loadHighScore() {
        File file = new File(HIGHSCORE_FILE);
        if (!file.exists())
            return 0;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void render() {

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < TOTAL_ROADS; i++) {
            gc.drawImage(Assets.img("road" + (i + 1) + ".png"), 0, roadY[i], WIDTH, HEIGHT);
        }

        player.draw(gc);
        enemies.forEach(e -> e.draw(gc));

        if (showBoom) {
            double boomWidth = 75;
            double boomHeight = 75;
            gc.drawImage(Assets.img("boom.png"), boomX, boomY, boomWidth, boomHeight);
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gc.fillText("Score: " + score, 20, 30);
        gc.fillText("HighScore: " + highScore, 20, 50);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public PlayerCar getPlayerCar() {
        return player;
    }

    public void showBoom(double x, double y) {
        boomX = x;
        boomY = y;
        showBoom = true;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
}
