
import java.awt.*;

/**
 * Playes class that hold all information about player.
 * Created by Marko on 5/13/2016.
 */
public class Player {
    private static Color PLAYER_COLOR = Color.blue;
    private static double PLAYER_XR_SPEED = 0.2;
    private static double PLAYER_YUP_SPEED = 0.2;
    private static double PLAYER_XL_SPEED = 0.2;
    private static double PLAYER_YDOWN_SPEED = 0.2;
    private static final int PLAYER_SIZE = 30;
    private static double PLAYER_X_COORDINATE = 50;
    private static double PLAYER_Y_COORDINATE = GameMaster.GAME_HEIGHT - 100;
    private static int LIFE_REMAINING = 3;

    /**
     * Return players X speed in "right" direction.
     */
    public static double getPlayerXrSpeed() {
        return PLAYER_XR_SPEED;
    }

    /**
     * Return players Y speed in "up" direction.
     */
    public static double getPlayerYupSpeed() {
        return PLAYER_YUP_SPEED;
    }

    /**
     * Return players X speed in "left" direction.
     */
    public static double getPlayerXlSpeed() {
        return PLAYER_XL_SPEED;
    }

    /**
     * Return player Y speed in "down" direction.
     */
    public static double getPlayerYdownSpeed() {
        return PLAYER_YDOWN_SPEED;
    }

    /**
     * Return player X coordinate.
     */
    public static double getPlayerXCoordinate() {
        return PLAYER_X_COORDINATE;
    }

    /**
     * Return player Y coordinate.
     */
    public static double getPlayerYCoordinate() {
        return PLAYER_Y_COORDINATE;
    }

    /**
     * Set player X speed in "right" direction to 0.
     */
    public static void resetPlayerXrSpeed() {
        PLAYER_XR_SPEED = 0;
    }

    /**
     * Set player X speed in "left" direction to 0.
     */
    public static void resetPlayerXlSpeed() {
        PLAYER_XL_SPEED = 0;
    }

    /**
     * Set player Y speed in "up" direction to 0.
     */
    public static void resetPlayerYupSpeed() {
        PLAYER_YUP_SPEED = 0;
    }

    /**
     * Set player Y speed in "down" direction to 0.
     */
    public static void resetPlayerYdownSpeed() {
        PLAYER_YDOWN_SPEED = 0;
    }

    /**
     * Return players size.
     */
    public static int getPlayerSize() {
        return PLAYER_SIZE;
    }

    /**
     * Change player X coordinate.
     *
     * @param newCoordinate
     */
    public static void changePlayerXCoordinate(double newCoordinate) {
        PLAYER_X_COORDINATE = newCoordinate;
    }

    /**
     * Change players Y cordinate.
     *
     * @param newCoordinate
     */
    public static void changePlayerYCoordinate(double newCoordinate) {
        PLAYER_Y_COORDINATE = newCoordinate;
    }

    /**
     * Return number of life remaining.
     */
    public static int getLifeRemaining() {
        return LIFE_REMAINING;
    }

    /**
     * Decrease number of players life with 1.
     */
    public static void loseALife() {
        if (LIFE_REMAINING > 0) LIFE_REMAINING--;
    }

    /**
     * Increase number of players life with 1/
     */
    public static void addAlife() {
        if (LIFE_REMAINING < 3) {
            LIFE_REMAINING++;
        }
    }

    /**
     * Add value to player X coordinate.
     *
     * @param add value to be added.
     */
    public static void addToPlayerXCoordinate(double add) {
        PLAYER_X_COORDINATE += add;
    }

    /**
     * Add value to player X coordinate.
     *
     * @param add value to be added.
     */
    public static void addToPlayerYCoordinate(double add) {
        PLAYER_Y_COORDINATE += add;
    }

    /**
     * Return players current color.
     */
    public static Color getPlayerColor() {
        return PLAYER_COLOR;
    }

    /**
     * Reset players speed to default.
     */
    public static void resetPlayerSpeedToDefault() {
        PLAYER_YUP_SPEED = 0.2;
        PLAYER_YDOWN_SPEED = 0.2;
        PLAYER_XL_SPEED = 0.2;
        PLAYER_XR_SPEED = 0.2;
    }

    /**
     * Change the players color.
     *
     * @param color new color
     */
    public static void changePlayersColor(Color color) {
        PLAYER_COLOR = color;
    }

    /**
     * Reset player color to default.
     */
    public static void resetPlayerColor() {
        PLAYER_COLOR = Color.blue;
    }

    /**
     * Check if player is in the goal position.
     *
     * @return
     */
    public static boolean checkIfPlayerInGoal() {
        return (Player.getPlayerXCoordinate() > GameMaster.getGoal_x() && Player.getPlayerYCoordinate() < GameMaster.getGoalY() + GameMaster.getGoalHeight() - Player.getPlayerSize());
    }

    /**
     * Reset the number of players life to 3.
     */
    public static void resetLife(){
        LIFE_REMAINING = 3;
    }
}
