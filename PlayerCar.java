// PlayerCar.java
import javafx.scene.image.Image;

public class PlayerCar extends Car {
    private double speedX = 0;
    private double speedY = 0;
    private static final double MOVE_SPEED = 4.0;
    
    public PlayerCar(double x, double y, Image image) {
        super(x, y, 0.0, image);
    }
    
    @Override
    public void update() {
        x += speedX;
        y += speedY;
    }
    
    public void moveUp() { speedY = -MOVE_SPEED; }
    public void moveDown() { speedY = MOVE_SPEED; }
    public void moveRight() { speedX = MOVE_SPEED; }
    public void moveLeft() { speedX = -MOVE_SPEED; }
    
    public void stopVertical() { speedY = 0; }
    public void stopHorizontal() { speedX = 0; }
}