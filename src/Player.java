public class Player {
    private double x;
    private double y;
    private final double width = 20;
    private final double height = 20;
    private double velocityY = 0;
    private boolean facingRight = true;

    // Constructor to initialize player's position
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Setters for position
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Getters for position
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Getter and setter for vertical velocity
    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    // Respawn the player to a specific spawn point
    public void respawn(int[] spawnPoint) {
        this.x = spawnPoint[0];
        this.y = spawnPoint[1];
        this.velocityY = 0;
    }

    // Draw the player image depending on facing direction
    public void draw(boolean facingRight) {
        String imagePath = this.facingRight ? "ElephantRight.png" : "ElephantLeft.png";
        StdDraw.picture(x + width / 2, y + height / 2, imagePath, width, height);
    }

    // Set and get the direction the player is facing
    public void setFacingRight(boolean right) {
        this.facingRight = right;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

}
