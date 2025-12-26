import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Random;

public class RandomCars {
    private Pane parent;
    private EnemyCar[] enemies;

    public RandomCars(Pane parent) {
        this.parent = parent;
        enemies = new EnemyCar[3];
        enemies[0] = new EnemyCar(50, 0, ""src/images/car_left_1.png"");
        enemies[1] = new EnemyCar(150, -200, ""src/images/car_left_2.png"");
        enemies[2] = new EnemyCar(250, -400, ""src/images/car_right_1.png"");
    }

    public void update() {
        for (EnemyCar car : enemies) car.update();
    }

    private class EnemyCar {
        private ImageView view;
        private int speed = 5;
        private Random rand = new Random();

        public EnemyCar(int x, int y, String imgPath) {
            Image img = new Image(""file:"" + imgPath);
            view = new ImageView(img);
            view.setX(x);
            view.setY(y);
            view.setFitWidth(50);
            view.setFitHeight(100);
            parent.getChildren().add(view);
        }

        public void update() {
            double newY = view.getY() + speed;
            if (newY > 600) {
                newY = 0;
                view.setX(rand.nextInt(350));
            }
            view.setY(newY);
        }
    }
}
