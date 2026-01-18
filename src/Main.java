import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Set up the game window size and coordinate system
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 600);
        StdDraw.enableDoubleBuffering();

        int nullButton = -1; // Used for stages without an UP key

        ArrayList<Stage> stages = new ArrayList<>();

        // Stage 1 - Basic controls
        stages.add(new Stage(-0.90, 3.65, 15, 0, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Arrow keys are required", "Arrow keys move player, press button and enter the second pipe"));

        // Stage 2 - Reversed left and right keys
        stages.add(new Stage(-0.90, 3.65, 15, 1, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
                "Not always straight forward", "Right and left buttons reversed"));

        // Stage 3 - Auto jump stage
        stages.add(new Stage(-2, 3.65, 24, 2, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, nullButton,
                "A bit bouncy here", "You jump constantly"));

        // Stage 4 - Press the button 5 times to open the door
        stages.add(new Stage(-0.90, 3.65, 15, 3, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Never gonna give you up", "Press button 5 times"));

        // Stage 5 - Time-limited door opening (5 seconds)
        stages.add(new Stage(-0.90, 3.65, 15, 4, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Hurry up!", "You have 5 seconds before the door closes"));

        // Start the game
        Game game = new Game(stages);
        game.play();
    }
}
