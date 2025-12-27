package com.autonest;

import javafx.scene.image.Image;
import java.util.Random;

public class RandomCar extends Car {

    public RandomCar(double x, double y, double speed, Image sprite) {
        super(x, y, speed, sprite);
    }

    public void setRandomLane(double[] lanes) {
        Random rand = new Random();
        x = lanes[rand.nextInt(lanes.length)];
    }

    @Override
    public void move() {
        y += speed; // move downward toward player
    }
}
