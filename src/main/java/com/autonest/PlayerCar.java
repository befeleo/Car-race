package com.autonest;

public class PlayerCar extends Car {

    public double speedX = 0;
    public double speedY = 0;

    public PlayerCar() {
        super(
                230, // start X
                350, // start Y
                50, // width
                90, // height
                0, // speed (manual)
                Assets.img("carself.png"));
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        // Keep car on road (no grass)
        x = Math.max(120, Math.min(330, x));
        y = Math.max(120, Math.min(380, y));
    }
}
