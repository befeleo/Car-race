import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Road {
    private Pane parent;
    private Rectangle[] lines;

    public Road(Pane parent) {
        this.parent = parent;
        lines = new Rectangle[10];
        for (int i = 0; i < 10; i++) {
            Rectangle line = new Rectangle(190, i*60, 20, 40);
            line.setFill(Color.WHITE);
            parent.getChildren().add(line);
            lines[i] = line;
        }
    }

    public void update() {
        for (Rectangle line : lines) {
            line.setY(line.getY() + 5);
            if (line.getY() > 600) line.setY(0);
        }
    }
}
