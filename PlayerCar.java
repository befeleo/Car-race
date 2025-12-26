// PlayerCar.java - Score-based speed increase
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerCar extends Car {
    private double speedX = 0;
    private double speedY = 0;
    
    // Speed settings
    private double baseMoveSpeed = 2.0;      // Starting speed
    private double currentMoveSpeed = 2.0;   // Current speed
    
    // Score-based speed settings
    private static final double SPEED_INCREASE_AMOUNT = 0.5;    // Increase by 0.5 each level
    private static final int SCORE_FOR_SPEED_UP = 10;           // Increase speed every 10 points
    private static final double MAX_SPEED = 8.0;                // Maximum speed limit
    
    // Boundary constraints from GameFX
    private static final int MIN_X = 0;
    private static final int MAX_X = 407;
    private static final int MIN_Y = 124;
    private static final int MAX_Y = 314;
    
    public PlayerCar(double x, double y, Image image) {
        super(x, y, 0.0, image);  // Fixed: 0.0 instead of 0
    }
    
    @Override
    public void update() {
        // Update position with current speed
        x += speedX * (currentMoveSpeed / baseMoveSpeed);
        y += speedY * (currentMoveSpeed / baseMoveSpeed);
        
        // Boundary checking (stops car at edges)
        if (x < MIN_X) x = MIN_X;
        if (x > MAX_X) x = MAX_X;
        if (y < MIN_Y) y = MIN_Y;
        if (y > MAX_Y) y = MAX_Y;
    }
    
    // MAIN METHOD: Update speed based on score
    public void updateSpeedBasedOnScore(int score) {
        // Calculate speed level based on score
        int speedLevel = score / SCORE_FOR_SPEED_UP;
        
        // Calculate new speed
        double newSpeed = baseMoveSpeed + (speedLevel * SPEED_INCREASE_AMOUNT);
        
        // Apply maximum limit
        currentMoveSpeed = Math.min(newSpeed, MAX_SPEED);
    }
    
    // Reset speed to base (for game restart)
    public void resetSpeed() {
        currentMoveSpeed = baseMoveSpeed;
        System.out.println("Speed reset to: " + currentMoveSpeed);
    }
    
    // Movement methods
    public void moveUp() { 
        speedX = baseMoveSpeed;
    }
    
    public void moveDown() { 
        speedX = -baseMoveSpeed; 
    }
    
    public void moveRight() { 
        speedY = baseMoveSpeed; 
    }
    
    public void moveLeft() { 
        speedY = -baseMoveSpeed; 
    }
    
    // Stop methods
    public void stopVertical() { speedX = 0; }
    public void stopHorizontal() { speedY = 0; }
    
    // Drawing capability
    public void draw(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, x, y);
        } else {
            gc.setFill(Color.RED);
            gc.fillRect(x, y, 93, 46);
        }
    }
    
    // Getters for speed information
    public double getCurrentSpeed() { return currentMoveSpeed; }
    public double getBaseSpeed() { return baseMoveSpeed; }
    
    // Get current speed level (how many times speed increased)
    public int getSpeedLevel() {
        return (int)((currentMoveSpeed - baseMoveSpeed) / SPEED_INCREASE_AMOUNT);
    }
    
    // Check if speed can still increase
    public boolean canIncreaseSpeed() {
        return currentMoveSpeed < MAX_SPEED;
    }
    
    // Get progress to next speed level (0.0 to 1.0)
    public double getSpeedProgress(int score) {
        int currentLevel = score / SCORE_FOR_SPEED_UP;
        int nextLevelScore = (currentLevel + 1) * SCORE_FOR_SPEED_UP;
        int scoreInCurrentLevel = score % SCORE_FOR_SPEED_UP;
        
        return (double) scoreInCurrentLevel / SCORE_FOR_SPEED_UP;
    }
}