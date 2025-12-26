import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class PlayerCar extends Car {
    private double speedX = 0;
    private double speedY = 0;
    private final double MOVE_SPEED = 4.0; // Increased speed
    
    public PlayerCar(double x, double y, Image image) {
        super(x, y, 0, image);
    }
    
    @Override
    public void update() {
        x += speedX;
        y += speedY;
    }
    
    // Movement methods
    public void moveUp() { speedX = MOVE_SPEED; }
    public void moveDown() { speedX = -MOVE_SPEED; }
    public void moveRight() { speedY = MOVE_SPEED; }
    public void moveLeft() { speedY = -MOVE_SPEED; }
    
    // Stop methods - FIXED NAMES
    public void stopVertical() { speedX = 0; }
    public void stopHorizontal() { speedY = 0; }
    
    // Collision detection
    public boolean collidesWith(Car other) {
        return getRight() > other.getX() && 
               getX() < other.getRight() &&
               getBottom() > other.getY() && 
               getY() < other.getBottom();
    }
}