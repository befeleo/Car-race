package com.autonest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game {

    private PlayerCar player;
    private List<RandomCar> randomCars = new ArrayList<>();

    // lanes in pixels inside the road
    private double[] lanes = {100, 200, 300, 400}; 

    private Random random = new Random();
    private boolean paused = false;
    private boolean gameOver = false;
    private int score = 0;
    private int highScore = 0;

    private Image[] roadFrames = new Image[5];
    private int roadFrameIndex = 0;
    private long lastRoadFrameTime = 0;

    public Game(PlayerCar player) {
        this.player = player;

        // load road frames
        for (int i = 0; i < 5; i++) {
            roadFrames[i] = new Image(getClass().getResource("/images/road" + (i+1) + ".png").toExternalForm());
        }
    }

    public void update() {
        if (paused || gameOver) return;

        player.update();

        spawnRandomCar();

        Iterator<RandomCar> it = randomCars.iterator();
        while (it.hasNext()) {
            RandomCar car = it.next();
            car.move(); // downward

            if (player.collidesWith(car)) {
                gameOver = true;
                break;
            }

            if (car.getY() > 500) {
                it.remove();
                score++;
                highScore = Math.max(score, highScore);
            }
        }

        // road animation slower (smooth)
        if (System.currentTimeMillis() - lastRoadFrameTime > 200) { 
            roadFrameIndex = (roadFrameIndex + 1) % roadFrames.length;
            lastRoadFrameTime = System.currentTimeMillis();
        }
    }

    private void spawnRandomCar() {
        if (randomCars.size() >= 4) return;

        if (Math.random() < 0.02) {
            boolean right = random.nextBoolean();
            int type = random.nextInt(3) + 1; // 1-3
            String imgName = right ? "car_right" + type + ".png" : "car_left" + type + ".png";
            Image img = new Image(getClass().getResource("/images/" + imgName).toExternalForm());

            RandomCar car = new RandomCar(0, -100, 2 + random.nextInt(2), img);
            car.setRandomLane(lanes);
            randomCars.add(car);
        }
    }

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, 500, 500);

        // draw road
        gc.drawImage(roadFrames[roadFrameIndex], 0, 0, 500, 500);

        // draw random cars
        for (RandomCar car : randomCars) car.draw(gc);

        // draw player
        player.draw(gc);

        // draw score
        gc.setFont(javafx.scene.text.Font.font(20));
        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillText("Score: " + score, 20, 30);
        gc.fillText("High Score: " + highScore, 20, 60);

        if (paused) {
            gc.setFont(javafx.scene.text.Font.font(40));
            gc.setFill(javafx.scene.paint.Color.YELLOW);
            gc.fillText("PAUSED", 180, 250);
        }

        if (gameOver) {
            gc.setFont(javafx.scene.text.Font.font(40));
            gc.setFill(javafx.scene.paint.Color.RED);
            gc.fillText("GAME OVER", 130, 250);
        }
    }

    public void togglePause() { paused = !paused; }

    public void restart() {
        randomCars.clear();
        score = 0;
        player.x = 200; // center of road
        player.y = 400;
        gameOver = false;
        paused = false;
    }

    public boolean isGameOver() { return gameOver; }
}
