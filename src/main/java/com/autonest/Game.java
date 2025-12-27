package com.autonest;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Pane {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private final PlayerCar player = new PlayerCar();
    private final List<RandomCar> enemies = new ArrayList<>();
    private final Random random = new Random();

    private long lastSpawnTime = 0;
    private static final long SPAWN_COOLDOWN = 1_500_000_000L; 
    private double currentEnemySpeed = 3.0;
    private static final double SPEED_INCREMENT = 0.4; 

    private boolean gameOver = false;
    private int score = 0;
private boolean showBoom = false;
private double boomX, boomY;
private long boomStartTime = 0;
private final long BOOM_DURATION = 1_000_000_000;
    /* ---------- ROAD SCROLLING ---------- */
    private double roadY1 = 0;
    private double roadY2 = -HEIGHT;
    private static double ROAD_SPEED = 2;

    /* ---------- LANES ---------- */
    private static final double[] LANES = { 130, 185, 250, 300 };

    public Game() {
        getChildren().add(canvas);
    }

    /* ---------- CONTROLS ---------- */
    public void bindControls(Scene scene) {

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> player.speedX = -3;
                case RIGHT -> player.speedX = 3;
                case UP -> player.speedY = -3;
                case DOWN -> player.speedY = 3;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT, RIGHT -> player.speedX = 0;
                case UP, DOWN -> player.speedY = 0;
            }
        });
    }

    /* ---------- GAME LOOP ---------- */
    public void startGame() {
         // Reset game state
    gameOver = false;
    showBoom = false;
    ROAD_SPEED = 2;   // reset road speed
    player.speedX = 0;
    player.speedY = 0;
    enemies.forEach(e -> e.speed = 2); // reset existing enemies' speed

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

    /* ---------- ROAD ---------- */
    private void updateRoad() {
        roadY1 += ROAD_SPEED;
        roadY2 += ROAD_SPEED;

        if (roadY1 >= HEIGHT)
            roadY1 = -HEIGHT;
        if (roadY2 >= HEIGHT)
            roadY2 = -HEIGHT;
    }

    /* ---------- ENEMIES ---------- */
    private void updateEnemies() {
        enemies.forEach(RandomCar::update);

        enemies.removeIf(e -> {
            if (e.y > HEIGHT + 100) {
                score++;

                if (currentEnemySpeed < 10.0) {
                    currentEnemySpeed += SPEED_INCREMENT;
                }

                return true;
            }
            return false;
        });
    }

    private void spawnEnemies(long now) {
        if (gameOver || showBoom) return; 
        if (now - lastSpawnTime >= SPAWN_COOLDOWN && enemies.size() < 4) {

            double lane = LANES[random.nextInt(LANES.length)];

            boolean laneBusy = false;
            for (RandomCar enemy : enemies) {
                
                if (Math.abs(enemy.x - lane) < 10 && enemy.y < 200) {
                    laneBusy = true;
                    break;
                }
            }

            if (!laneBusy) {
                String side = random.nextBoolean() ? "car_left" : "car_right";
                String image = side + (random.nextInt(3) + 1) + ".png";

                enemies.add(new RandomCar(lane, image, currentEnemySpeed));
                lastSpawnTime = now;
            }
        }
    }

    /* ---------- COLLISION ---------- */
private void checkCollision() {
    for (RandomCar enemy : enemies) {
        if (player.collidesWith(enemy) && !showBoom) {
            boomX = player.getCollisionX(enemy) - 37; // center explosion
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

    // After 1 second of showing boom, end game
    if (showBoom && System.nanoTime() - boomStartTime > BOOM_DURATION) {
        gameOver = true;
    }
}

  /* ---------- RENDER ---------- */
    private void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Road
        gc.drawImage(Assets.img("road1.png"), 0, roadY1, WIDTH, HEIGHT);
        gc.drawImage(Assets.img("road2.png"), 0, roadY2, WIDTH, HEIGHT);

        // Cars
        player.draw(gc);
        enemies.forEach(e -> e.draw(gc));
if (showBoom) {
    double boomWidth = 75;  // desired width
    double boomHeight = 75; // desired height
    gc.drawImage(Assets.img("boom.png"), boomX, boomY, boomWidth, boomHeight);
}


        // HUD
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 20, 30);
    }

    public boolean isGameOver(){
        return gameOver;
    }

    /* ---------- GETTERS ---------- */
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

}
