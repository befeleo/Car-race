package com.autonest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Car {

    protected double x, y;
    protected double width, height;
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

    public void draw(GraphicsContext draw) {
        draw.drawImage(sprite, x, y, width, height);
    }

    public abstract void update();

    public boolean collidesWith(Car other) {
        double paddingX = width * 0.1;
        double paddingY = height * 0.1;

        double thisLeft = x + paddingX;
        double thisRight = x + width - paddingX;
        double thisTop = y + paddingY;
        double thisBottom = y + height - paddingY;

        double otherLeft = other.x + paddingX;
        double otherRight = other.x + other.width - paddingX;
        double otherTop = other.y + paddingY;
        double otherBottom = other.y + other.height - paddingY;

        return thisLeft < otherRight &&
                thisRight > otherLeft &&
                thisTop < otherBottom &&
                thisBottom > otherTop;
    }

    public double getCollisionX(Car other) {
        double left = Math.max(x, other.x);
        double right = Math.min(x + width, other.x + other.width);
        return ((left + right) / 2);
    }

    public double getCollisionY(Car other) {
        double top = Math.max(y, other.y);
        double bottom = Math.min(y + height, other.y + other.height);
        return ((top + bottom) / 2);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
