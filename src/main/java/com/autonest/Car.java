package com.autonest;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Car {
    protected double x;
    protected double y;
    protected double speed;
    protected Image image;
    protected double width;
    protected double height;

    public Car(double x, double y, double speed, java.awt.Image sprite) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = sprite;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

    public Car(int x2, int y2, int speed2, java.awt.Image sprite) {
        //TODO Auto-generated constructor stub
    }

    public abstract void update();

    public void draw(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, x, y);
        }
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

    public double getRight() {
        return x + width;
    }

    public double getBottom() {
        return y + height;
    }

    public Image getImage() {
        return image;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isOffScreen(double screenWidth) {
        return x + width < 0 || x > screenWidth || y + height < 0 || y > screenWidth;
    }

    public boolean collidesWith(Car other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }
}