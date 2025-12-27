package com.autonest;

public class RandomCar extends Car {

    public RandomCar(double laneX, String imageName, double speed) {
        super(
                laneX,
                -120, // start above screen
                50,
                90,
                speed,
                Assets.img(imageName));
    }

    @Override
    public void update() {
        y += speed; // move DOWN toward player
    }
}
