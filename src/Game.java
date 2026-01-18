import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    // Tracks the current stage index
    private int stageIndex = 0;

    // List of all stages
    private ArrayList<Stage> stages;

    // Counter for number of player deaths
    private int deathNumber = 0;

    // Used to track total play time
    private long startTime;

    // Flag to indicate game reset
    private boolean resetGame = false;

    // Prevents repeated mouse click registration
    boolean mousePreviouslyPressed;

    // Help message to show when "Help" is clicked
    String helpMessage = "";

    public Game(ArrayList<Stage> stages) {
        this.stages = stages;
    }

    public int getStageIndex() {
        return stageIndex;
    }

    public Stage getCurrentStage() {
        return stages.get(stageIndex);
    }

    // Main game loop
    public void play() {
        while (true) {
            Player player = new Player(120, 460);
            Stage currentStage = getCurrentStage();
            Map map = new Map(currentStage, player);

            map.respawn();
            startTime = System.currentTimeMillis();
            helpMessage = ""; // Reset help message at the beginning of a stage

            boolean stagePassed = false;
            while (!stagePassed) {
                StdDraw.pause(30);

                // Determine whether to show clue or help
                String clueOrHelp = (helpMessage != null && !helpMessage.isEmpty())
                        ? helpMessage
                        : (currentStage.getClue() != null ? currentStage.getClue() : "");

                map.draw(stageIndex, deathNumber, formatTime(System.currentTimeMillis() - startTime), clueOrHelp);

                handleInput(map);

                // Apply gravity if no directional key is pressed
                if (!StdDraw.isKeyPressed(map.getStage().getKeyCodes()[0]) &&
                        !StdDraw.isKeyPressed(map.getStage().getKeyCodes()[1])) {
                    map.applyGravity();
                }

                // Handle player death on spike collision
                if (map.hitSpike()) {
                    deathNumber++;
                    map.restartStage();
                }

                // Handle button press
                if (map.isOnButton()) {
                    if (!map.wasOnButtonLastFrame) {
                        map.pressButton();
                    }
                    map.wasOnButtonLastFrame = true;
                } else {
                    map.wasOnButtonLastFrame = false;
                }

                // Handle level completion
                if (map.atExitPipe()) {
                    stagePassed = true;
                }

                // Handle mouse click events
                if (StdDraw.isMousePressed()) {
                    if (!mousePreviouslyPressed) {
                        double mx = StdDraw.mouseX();
                        double my = StdDraw.mouseY();

                        // Help button
                        if (mx >= 230 && mx <= 270 && my >= 70 && my <= 100) {
                            helpMessage = map.getStage().getHelp();
                        }

                        // Restart button
                        if (mx >= 510 && mx <= 590 && my >= 70 && my <= 100) {
                            deathNumber++;
                            map.restartStage();
                        }

                        // Reset game button
                        if (mx >= 320 && mx <= 480 && my >= 5 && my <= 35) {
                            deathNumber = 0;
                            stageIndex = -1; // Will be incremented immediately after
                            resetGame = true;
                            break;
                        }

                        mousePreviouslyPressed = true;
                    }
                } else {
                    mousePreviouslyPressed = false;
                }
            }

            // Show stage passed banner if not resetting
            if (!resetGame) {
                showStagePassedBanner();
            }
            stageIndex++;

            // If final stage is reached, show end screen
            if (stageIndex == stages.size()) {
                showEndBanner();
                while (true) {
                    if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                        stageIndex = 0;
                        deathNumber = 0;
                        break;
                    } else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                        System.exit(0);
                    }
                }
            }

            if (resetGame) {
                resetGame = false;
                continue;
            }
        }
    }

    // Display the stage complete message (except last stage)
    private void showStagePassedBanner() {
        if (stageIndex == stages.size() - 1) return;

        StdDraw.setPenColor(new Color(0, 128, 0));
        StdDraw.filledRectangle(400, 300, 400, 60);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 22));
        StdDraw.text(400, 315, "You passed the stage");
        StdDraw.text(400, 285, "But is the level over?!");

        StdDraw.show();
        StdDraw.pause(2000);
    }

    // Display final congratulations screen
    private void showEndBanner() {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        String timeStr = formatTime(totalTime);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(400, 300, 400, 300);

        StdDraw.setPenColor(new Color(0, 128, 0));
        StdDraw.filledRectangle(400, 300, 400, 100);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 22));
        StdDraw.text(400, 340, "CONGRATULATIONS! YOU FINISHED THE LEVEL!");
        StdDraw.text(400, 300, "PRESS 'A' TO PLAY AGAIN or 'Q' TO QUIT");
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 18));
        StdDraw.text(400, 260, "You finished with " + deathNumber + " deaths in " + timeStr);

        StdDraw.show();
    }

    // Format time into mm:ss:SS format (hundredths of a second)
    private String formatTime(long timeMillis) {
        long seconds = (timeMillis / 1000) % 60;
        long minutes = (timeMillis / 1000) / 60;
        long ms = (timeMillis % 1000) / 10;
        return String.format("%02d:%02d:%02d", minutes, seconds, ms);
    }

    // Handle player input (movement and jump)
    private void handleInput(Map map) {
        int[] codes = map.getStage().getKeyCodes();

        if (StdDraw.isKeyPressed(codes[0])) {
            map.movePlayer('r');
        }
        if (StdDraw.isKeyPressed(codes[1])) {
            map.movePlayer('l');
        }
        if (codes[2] != -1 && StdDraw.isKeyPressed(codes[2]) && map.isOnGround()) {
            map.getPlayer().setVelocityY(map.getStage().getVelocityY());
        }
        if (map.getStage().getStageNumber() == 2 && map.isOnGround()) {
            map.getPlayer().setVelocityY(map.getStage().getVelocityY());
        }
    }
}
