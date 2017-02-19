
/**
 * Head class for the game. Starts the game upon called.
 */
public class GameMaster {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static final int GAMEMENU_WIDTH = 300;
    private static int GAME_LEVEL = 1;
    private static int GAME_DIFFICULTY = 1;
    private static boolean PLAY_GAMEOVER = true;
    private static boolean PLAY_AGAIN = false;

    private Level level;
    private boolean unfreezePlayer = true;
    private boolean rightKey;
    private boolean leftKey;
    private boolean upKey;
    private boolean downKey;
    private static final int GOAL_X = GAME_WIDTH - 150;
    private static final int GOAL_WIDTH = 150;
    private static final int GOAL_Y = 0;
    private static final int GOAL_HEIGHT = 150;

    /**
     * Creates the new GameMaster with start level.
     *
     * @param level of which to start the game.
     */
    public GameMaster(Level level) {
        this.level = level;
    }

    /**
     * Change the boolean value unfreezePlayer to make the player freeze of unfreeze them.
     *
     * @param unfreezePlayer
     */
    public void setUnfreezePlayer(boolean unfreezePlayer) {
        this.unfreezePlayer = unfreezePlayer;
    }

    /**
     * Set boolean value to true or false to simulate pushing and releasing the leftKey button.
     *
     * @param leftKey
     */
    public void setLeftKey(boolean leftKey) {
        this.leftKey = leftKey;
    }

    /**
     * Set boolean value to true or false to simulate pushing and releasing the rightKey button.
     *
     * @param rightKey
     */
    public void setRightKey(boolean rightKey) {
        this.rightKey = rightKey;
    }

    /**
     * Set boolean value to true or false to simulate pushing and releasing the upKey button.
     *
     * @param upKey
     */
    public void setUpKey(boolean upKey) {
        this.upKey = upKey;
    }

    /**
     * Set boolean value to true or false to simulate pushing and releasing the downKey button.
     *
     * @param downKey
     */
    public void setDownKey(boolean downKey) {
        this.downKey = downKey;
    }

    /**
     * Return goal X position.
     */
    public static int getGoal_x() {
        return GOAL_X;
    }

    /**
     * Return goal Y position.
     */
    public static int getGoalY() {
        return GOAL_Y;
    }

    /**
     * Return goal width.
     */
    public static int getGoalWidth() {
        return GOAL_WIDTH;
    }

    /**
     * Return goal height.
     */
    public static int getGoalHeight() {
        return GOAL_HEIGHT;
    }

    /**
     * Return current level.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Change curent level to new level.
     *
     * @param newLevel
     */
    public void changeLevel(Level newLevel) {
        level = newLevel;
    }

    /**
     * Return current game difficulty.
     */
    public static int getGameDifficulty() {
        return GAME_DIFFICULTY;
    }

    /**
     * Increase game difficulty by 1.
     */
    public static void increaseGameDifficulty() {
        if (GAME_DIFFICULTY < 5) GAME_DIFFICULTY++;
    }

    /**
     * Decrease game difficulty by 1.
     */
    public static void decreaseGameDifficulty() {
        if (GAME_DIFFICULTY > 1) GAME_DIFFICULTY--;
    }

    /**
     * Return number of current level.
     *
     * @return int number of current level.
     */
    public static int getGameLevel() {
        return GAME_LEVEL;
    }

    /**
     * Change the boolen value PLAY_GAMEOVER to stop "game over" sound.
     */
    public static void stopGameOverSound() {
        PLAY_GAMEOVER = false;
    }

    /**
     * Return PLAY_GAMEOVER boolean value which controls when sound should be played.
     */
    public static boolean getPlayGameover() {
        return PLAY_GAMEOVER;
    }

    /**
     * Set the PLAY_AGAIN boolen value to true in order to start the game from the beginning.
     * @param play
     */
    public static void setPlayAgain(boolean play) {
        PLAY_AGAIN = play;
    }

    /**
     * Resets the game from the level 1.
     */
    public void resetGame() {
        Player.resetLife();
        GAME_LEVEL = 1;
        changeLevel(new Level(GAME_LEVEL));
        PLAY_GAMEOVER = true;
    }

    /**
     * Simulates the movement of the objects in the game.
     *
     * @param level of the game.
     * @param time  between previous change in position.
     */
    public void tick(long time, Level level) {
        if (rightKey && unfreezePlayer && Player.getPlayerXCoordinate() + Player.getPlayerSize() < GAME_WIDTH)
            Player.addToPlayerXCoordinate(time * Player.getPlayerXrSpeed());
        if (leftKey && unfreezePlayer && Player.getPlayerXCoordinate() >= 0)
            Player.addToPlayerXCoordinate(-time * Player.getPlayerXlSpeed());
        if (upKey && unfreezePlayer && Player.getPlayerYCoordinate() >= 0)
            Player.addToPlayerYCoordinate(-time * Player.getPlayerYupSpeed());
        if (downKey && unfreezePlayer && Player.getPlayerYCoordinate() + Player.getPlayerSize() < GAME_HEIGHT)
            Player.addToPlayerYCoordinate(time * Player.getPlayerYdownSpeed());

        Player.resetPlayerSpeedToDefault();

        Physics.collisionWithWalls(level);
        Physics.horizontalBouncingSquares(level, time);
        Physics.verticalBouncingSquares(level, time);
        Physics.checkIfCandyIsTaken(level);
        Physics.verticalBouncingWalls(level, time);
        Physics.horizontalBouncingWalls(level, time);

        if (Player.checkIfPlayerInGoal()) {
            Physics.returnPlayer();
            setUnfreezePlayer(false);
            Player.addAlife();
            Player.resetPlayerColor();
            if (GAME_LEVEL < Reader.getNumberOfAvailableLevels()) {
                changeLevel(new Level(++GAME_LEVEL));
            }
        }

        if (PLAY_AGAIN && Player.getLifeRemaining() < 1) {
            resetGame();
            PLAY_AGAIN = false;
            Sound.playSoundtrack();
        }

    }
}


