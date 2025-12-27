package com.autonest;

public class RandomCar extends Car {

    public RandomCar(double laneX, String imageName, double speed) {
        super(laneX, -120, 50, 90, speed, Assets.img(imageName));
    }

    @Override
    public void update() {
        // If speed is somehow 0, force it to at least move slowly
        if (this.speed <= 0)
            this.speed = 2.0;

        this.y += this.speed;
         x = Math.max(170, Math.min(450, x));
    }
}