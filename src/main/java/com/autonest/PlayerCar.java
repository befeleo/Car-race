package com.autonest;

import javafx.scene.image.Image;

public class PlayerCar extends Car {
    private double speedX = 0;
    private final double MOVE_SPEED = 5.0;

    public PlayerCar(double x, double y, Image image) {
        super(x, y, 0, image);
    }

    public void update() {
        x += speedX;

        // restrict inside road (assuming road from x=50 to x=450)
        if (x < 50) x = 50;
        if (x > 450) x = 450;
    }

    public void moveLeft() { speedX = -MOVE_SPEED; }
    public void moveRight() { speedX = MOVE_SPEED; }
    public void stopHorizontal() { speedX = 0; }

    public boolean collidesWith(Car other) {
        return getRight() > other.getX() &&
               getX() < other.getRight() &&
               getBottom() > other.getY() &&
               getY() < other.getBottom();
    }

    public void setSprite(Image image) { this.sprite = image; }
}
