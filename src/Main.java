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
        for (Rectan



# --- CONFIG ---
C:\Users\degef\Car-race = "C:\Users\degef\Car-race"
C:\Users\degef\Downloads\javafx-sdk-25.0.1\lib = "C:\Users\degef\Downloads\javafx-sdk-25.0.1\lib"
https://github.com/befeleo/Car-race.git = "https://github.com/befeleo/Car-race.git"

# --- CLEAN PREVIOUS PROJECT (Optional) ---
if (Test-Path C:\Users\degef\Car-race) { Remove-Item -Recurse -Force C:\Users\degef\Car-race }

# --- CREATE PROJECT STRUCTURE ---
mkdir C:\Users\degef\Car-race
cd C:\Users\degef\Car-race
mkdir src, src\images, out

# --- CREATE JAVA FILES ---

# Main.java
@"
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        Scene scene = new Scene(game, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Car Racing");
        primaryStage.setResizable(false);
        primaryStage.show();
        game.startGame();
        game.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
