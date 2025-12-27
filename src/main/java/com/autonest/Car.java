package com.autonest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Car {

    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double speed;
    protected Image sprite;

    public Car(double x, double y, double width, double height, double speed, Image sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.sprite = sprite;
    }

    public abstract void update();

    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite, x, y, width, height);
    }

    public boolean collidesWith(Car other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }
}
