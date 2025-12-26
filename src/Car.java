import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Car {
    private ImageView carView;
    private int speed = 0;
    private Pane parent;

    public Car(int x, int y, Pane parent) {
        this.parent = parent;
        Image img = new Image(""file:src/images/car_self.png"");
        carView = new ImageView(img);
        carView.setX(x);
        carView.setY(y);
        carView.setFitWidth(50);
        carView.setFitHeight(100);
        parent.getChildren().add(carView);
    }

    public void moveLeft() { speed = -5; }
    public void moveRight() { speed = 5; }
    public void stop() { speed = 0; }

    public void update() {
        double newX = carView.getX() + speed;
        if (newX < 0) newX = 0;
        if (newX > 350) newX = 350;
        carView.setX(newX);
    }
}
