import java.awt.*;
import java.util.Random;

// Todo: Java AWT Image sprit?

public class RandomCar extends Car {

    public RandomCar(int x, int y, int speed, Image sprite) {
        super(x, y, speed, sprite);
    }

    public void setRandomLane(int[] lanes) {
        Random rand = new Random();
        x = lanes[rand.nextInt(lanes.length)];
    }

}
