import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

public class Game extends Pane {
    private Car playerCar;
    private Road road;
    private RandomCars randomCars;

    public Game() {
        setPrefSize(400, 600);
        playerCar = new Car(180, 500, this);
        road = new Road(this);
        randomCars = new RandomCars(this);

        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) playerCar.moveLeft();
            if (e.getCode() == KeyCode.RIGHT) playerCar.moveRight();
        });

        setOnKeyReleased(e -> playerCar.stop());
    }

    public void startGame() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                road.update();
                playerCar.update();
                randomCars.update();
            }
        };
        timer.start();
    }
}
