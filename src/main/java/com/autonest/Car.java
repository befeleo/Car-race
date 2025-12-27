package com.autonest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Car {
    protected double x;
    protected double y;
    protected double speed;
    protected Image sprite;

    public Car(double x, double y, double speed, Image sprite) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.sprite = sprite;
    }

    public void move() {
        y += speed; // move downward
    }

    public void draw(GraphicsContext gc) {
        gc.save();

        // Rotate image so that it faces UP vertically
        gc.translate(x + sprite.getWidth()/2, y + sprite.getHeight()/2);
        gc.rotate(-90); // rotate counterclockwise 90 degrees
        gc.drawImage(sprite, -sprite.getWidth()/2, -sprite.getHeight()/2);

        gc.restore();
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getRight() { return x + sprite.getWidth(); }
    public double getBottom() { return y + sprite.getHeight(); }
}
