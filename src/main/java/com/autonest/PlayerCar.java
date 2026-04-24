package com.autonest;

public class PlayerCar extends Car {

    public double speedX = 0;
    public double speedY = 0;

    public PlayerCar() {
        super(230, 350, 50, 90, 0, Assets.img("carself.png"));
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        x = Math.max(170, Math.min(450, x));
        y = Math.max(50, Math.min(575, y));
    }
}
