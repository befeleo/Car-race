import java.awt.*;

// Todo: Java AWT Image sprit?

public class PlayerCar extends Car {

    public PlayerCar(int x, int y, int speed, Image sprite) {
        super(x, y, speed, sprite);
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

}
