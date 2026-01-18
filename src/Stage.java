import java.awt.*;
import java.util.Random;

public class Stage {
    private int stageNumber; // Identifier for the stage
    private double gravity;  // Gravity value for this stage
    private double velocityX; // Horizontal velocity for player movement
    private double velocityY; // Jump velocity or initial Y velocity
    private int rightCode;    // Key code for moving right
    private int leftCode;     // Key code for moving left
    private Integer upCode;   // Key code for jumping (can be null)
    private String clue;      // Clue message for the stage
    private String help;      // Help message when help is requested
    private Color color;      // Unique color theme for the stage

    // Constructor to initialize all stage parameters
    public Stage(double gravity, double velocityX, double velocityY, int stageNumber,
                 int rightCode, int leftCode, Integer upCode, String clue, String help) {
        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.upCode = upCode;
        this.clue = clue;
        this.help = help;
        this.color = getRandomColor(); // Assign a random color to the stage
    }

    // Generates a random color for stage visuals
    private Color getRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    // Getters for stage properties
    public int getStageNumber() {
        return stageNumber;
    }

    public double getGravity() {
        return gravity;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    // Returns key codes in an array (right, left, up)
    public int[] getKeyCodes() {
        return new int[]{rightCode, leftCode, upCode == null ? -1 : upCode};
    }

    public String getClue() {
        return clue != null ? clue : "";
    }

    public String getHelp() {
        return help != null ? help : "";
    }

    public Color getColor() {
        return color;
    }
}
