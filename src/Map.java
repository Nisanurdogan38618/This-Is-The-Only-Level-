import java.awt.*;

public class Map {
    // Stage and player references
    private Stage stage;
    private Player player;

    // Map elements
    private int[][] obstacles;
    private int[] button;
    private int[] buttonFloor;
    private int[][] startPipe;
    private int[][] exitPipe;
    private int[][] spikes;
    private int[] door;

    // Door/button logic
    private int buttonPressNum = 0;
    private boolean isDoorOpen = false;
    boolean wasOnButtonLastFrame = false;
    String helpText;
    private long doorOpenedTime = 0;
    private final int DOOR_OPEN_DURATION_MS = 5000;
    private final int MAX_PRESS = 5; // Stage 4 button press requirement

    public Map(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;

        // Initialize map elements
        this.obstacles = new int[][] {
                {0, 120, 120, 270}, {0, 270, 168, 330}, {0, 330, 30, 480}, {0, 480, 180, 600},
                {180, 570, 680, 600}, {270, 540, 300, 570}, {590, 540, 620, 570}, {680, 510, 800, 600},
                {710, 450, 800, 510}, {740, 420, 800, 450}, {770, 300, 800, 420}, {680, 240, 800, 300},
                {680, 300, 710, 330}, {770, 180, 800, 240}, {0, 120, 800, 150}, {560, 150, 800, 180},
                {530, 180, 590, 210}, {530, 210, 560, 240}, {320, 150, 440, 210}, {350, 210, 440, 270},
                {220, 270, 310, 300}, {360, 360, 480, 390}, {530, 310, 590, 340}, {560, 400, 620, 430}
        };

        this.button = new int[] {400, 390, 470, 410};
        this.buttonFloor = new int[] {400, 390, 470, 400};
        this.startPipe = new int[][] {{115, 450, 145, 480}, {110, 430, 150, 450}};
        this.exitPipe = new int[][] {{720, 175, 740, 215}, {740, 180, 770, 210}};
        this.spikes = new int[][] {
                {30, 333, 50, 423}, {121, 150, 207, 170}, {441, 150, 557, 170},
                {591, 180, 621, 200}, {750, 301, 769, 419}, {680, 490, 710, 510}, {401, 550, 521, 570}
        };
        this.door = new int[] {685, 180, 700, 240};
    }

    // Apply gravity to the player
    public void applyGravity() {
        double dy = player.getVelocityY();
        dy += stage.getGravity();
        double nextY = player.getY() + dy;

        if (!collides(player.getX(), nextY)) {
            player.setY(nextY);
            player.setVelocityY(dy);
        } else {
            player.setVelocityY(0);
        }
    }

    // Move player left or right
    public void movePlayer(char direction) {
        double dx = 0;
        double dy = player.getVelocityY();

        if (direction == 'r') {
            dx = stage.getVelocityX();
            player.setFacingRight(true);
        } else if (direction == 'l') {
            dx = -stage.getVelocityX();
            player.setFacingRight(false);
        }

        double nextX = player.getX() + dx;
        double nextY = player.getY() + dy;

        if (!collides(nextX, player.getY())) {
            player.setX(nextX);
        }

        dy += stage.getGravity();
        nextY = player.getY() + dy;

        if (!collides(player.getX(), nextY)) {
            player.setY(nextY);
            player.setVelocityY(dy);
        } else {
            player.setVelocityY(0);
        }
    }

    // Check collisions with environment or closed door
    private boolean collides(double nextX, double nextY) {
        for (int[] obs : obstacles) {
            if (checkCollision(nextX, nextY, obs)) return true;
        }
        if (!isDoorOpen && checkCollision(nextX, nextY, door)) return true;
        return false;
    }

    // Collision helper method
    private boolean checkCollision(double x, double y, int[] rect) {
        return x + 20 > rect[0] && x < rect[2] && y + 20 > rect[1] && y < rect[3];
    }

    // Check if player is on the ground
    public boolean isOnGround() {
        return collides(player.getX(), player.getY() - 1);
    }

    // Check if player is on the button
    public boolean isOnButton() {
        return checkCollision(player.getX(), player.getY(), buttonFloor);
    }

    // Handle button logic depending on stage number
    public void pressButton() {
        if (stage.getStageNumber() == 4) {
            isDoorOpen = true;
            doorOpenedTime = System.currentTimeMillis();
        } else if (stage.getStageNumber() == 3) {
            buttonPressNum++;
            if (buttonPressNum >= MAX_PRESS) isDoorOpen = true;
        } else {
            isDoorOpen = !isDoorOpen;
        }
    }

    // Check if player reached exit pipe
    public boolean atExitPipe() {
        return checkCollision(player.getX(), player.getY(), exitPipe[0]);
    }

    // Check if player hit any spike
    public boolean hitSpike() {
        for (int[] spike : spikes) {
            if (checkCollision(player.getX(), player.getY(), spike)) return true;
        }
        return false;
    }

    // Reset stage to initial state
    public void restartStage() {
        buttonPressNum = 0;
        isDoorOpen = false;
        wasOnButtonLastFrame = false;
        respawn();
    }

    // Respawn player at initial coordinates
    public void respawn() {
        player.respawn(new int[]{120, 460});
    }

    public Stage getStage() {
        return stage;
    }

    public Player getPlayer() {
        return player;
    }

    // Draw entire frame with status UI and gameplay elements
    public void draw(int stageNumber, int deathCounter, String timerText, String clueText) {
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 15));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(400, 300, 400, 300);

        if (stage.getStageNumber() == 4 && isDoorOpen) {
            long now = System.currentTimeMillis();
            if (now - doorOpenedTime > DOOR_OPEN_DURATION_MS) {
                isDoorOpen = false;
            }
        }

        // Bottom HUD background
        StdDraw.setPenColor(new Color(56, 93, 172));
        StdDraw.filledRectangle(400, 60, 400, 60);

        // UI text and buttons
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(250, 85, "Help");
        StdDraw.rectangle(250, 85, 40, 15);
        StdDraw.text(550, 85, "Restart");
        StdDraw.rectangle(550, 85, 40, 15);
        StdDraw.text(400, 20, "RESET THE GAME");
        StdDraw.rectangle(400, 20, 80, 15);

        StdDraw.text(700, 75, "Deaths: " + deathCounter);
        StdDraw.text(700, 50, "Stage: " + (stageNumber + 1));
        StdDraw.text(100, 50, timerText);
        StdDraw.text(100, 75, "Level: 1");

        // Show clue/help and door timer if active
        if (clueText != null && !clueText.isEmpty()) {
            if (clueText.equals(stage.getHelp())) {
                StdDraw.text(400, 85, "Help:");
            } else {
                StdDraw.text(400, 85, "Clue:");
            }
            StdDraw.text(400, 55, clueText);
        }

        if (stage.getStageNumber() == 4 && isDoorOpen) {
            long remaining = DOOR_OPEN_DURATION_MS - (System.currentTimeMillis() - doorOpenedTime);
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(400, 105, "Door closes in: " + (remaining / 1000.0) + " s");
        }

        drawEnvironment();
        StdDraw.show();
    }

    // Draw map elements like obstacles, pipes, spikes, etc.
    private void drawEnvironment() {
        StdDraw.setPenColor(stage.getColor());
        for (int[] obs : obstacles) {
            StdDraw.filledRectangle((obs[0] + obs[2]) / 2.0, (obs[1] + obs[3]) / 2.0,
                    (obs[2] - obs[0]) / 2.0, (obs[3] - obs[1]) / 2.0);
        }

        // Draw button in two parts
        double centerX = (button[0] + button[2]) / 2.0;
        double centerY = (button[1] + button[3]) / 2.0;
        double width = (button[2] - button[0]) / 2.0;
        double height = (button[3] - button[1]) / 2.0;

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(centerX, centerY - height / 2, width, height / 2);

        if (!isOnButton()) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.filledRectangle(centerX, centerY + height / 2, width, height / 2);
        }

        if (!isDoorOpen) {
            StdDraw.setPenColor(Color.DARK_GRAY);
            StdDraw.filledRectangle((door[0] + door[2]) / 2.0, (door[1] + door[3]) / 2.0,
                    (door[2] - door[0]) / 2.0, (door[3] - door[1]) / 2.0);
        }

        // Draw pipes
        StdDraw.setPenColor(Color.GREEN);
        for (int[] pipe : startPipe) {
            StdDraw.filledRectangle((pipe[0] + pipe[2]) / 2.0, (pipe[1] + pipe[3]) / 2.0,
                    (pipe[2] - pipe[0]) / 2.0, (pipe[3] - pipe[1]) / 2.0);
        }
        for (int[] pipe : exitPipe) {
            StdDraw.filledRectangle((pipe[0] + pipe[2]) / 2.0, (pipe[1] + pipe[3]) / 2.0,
                    (pipe[2] - pipe[0]) / 2.0, (pipe[3] - pipe[1]) / 2.0);
        }

        // Draw spikes with orientation logic
        for (int[] spike : spikes) {
            double sx1 = spike[0], sy1 = spike[1];
            double sx2 = spike[2], sy2 = spike[3];
            double spikeCenterX = (sx1 + sx2) / 2.0;
            double spikeCenterY = (sy1 + sy2) / 2.0;

            double spikeWidth = Math.abs(sx2 - sx1);
            double spikeHeight = Math.abs(sy2 - sy1);

            if (spikeCenterY > 200 && spikeCenterY < 450) {
                double temp = spikeWidth;
                spikeWidth = spikeHeight;
                spikeHeight = temp;
            }

            double spikeAngle = 0;
            if (spikeCenterY <= 200) spikeAngle = 180;
            else if (spikeCenterY >= 450) spikeAngle = 0;
            else if (spikeCenterX <= 400) spikeAngle = 90;
            else spikeAngle = 270;

            StdDraw.picture(spikeCenterX, spikeCenterY, "Spikes.png", spikeWidth, spikeHeight, spikeAngle);
        }

        player.draw(player.isFacingRight());
    }
}