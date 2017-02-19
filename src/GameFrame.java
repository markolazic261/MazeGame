import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Sets up a frame of which the game will be viewed on and paint all obstacles.
 */
public class GameFrame extends JPanel {
    private final GameMaster gameMaster;
    private final int height;
    private final int width;

    public GameFrame(final GameMaster game) {
        this.gameMaster = game;
        height = GameMaster.GAME_HEIGHT;
        width = GameMaster.GAME_WIDTH + GameMaster.GAMEMENU_WIDTH;
        this.setFocusable(true);
        this.requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) gameMaster.setLeftKey(true);
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) gameMaster.setRightKey(true);
                if (e.getKeyCode() == KeyEvent.VK_UP) gameMaster.setUpKey(true);
                if (e.getKeyCode() == KeyEvent.VK_DOWN) gameMaster.setDownKey(true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    gameMaster.setLeftKey(false);
                    gameMaster.setUnfreezePlayer(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    gameMaster.setRightKey(false);
                    gameMaster.setUnfreezePlayer(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    gameMaster.setUpKey(false);
                    gameMaster.setUnfreezePlayer(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    gameMaster.setDownKey(false);
                    gameMaster.setUnfreezePlayer(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) GameMaster.increaseGameDifficulty();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) GameMaster.decreaseGameDifficulty();
                if (e.getKeyCode() == KeyEvent.VK_A && Player.getLifeRemaining() < 1) GameMaster.setPlayAgain(true);
            }
        });
    }

    @Override
    /**
     * Paints out the gameboard, player, obstacles and menu if the game isn't over.
     * Paints "game over" otherwise.
     */
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (Player.getLifeRemaining() > 0) {
            paintBackground(g2d);
            paintPlayer(g2d);
            paintObstacles(g2d);
            paintBordersOfTheMenu(g2d);
            paintMenu(g2d);
            paintHearts(g2d);
            paintLevelNumber(g2d);
        } else {
            paintBlackBackground(g2d);
            paintGameOver(g2d);
           Sound.stopSoundtrack();
            paintPlayAgain(g2d);
            if (GameMaster.getPlayGameover()) {
                Sound.playGameOver();
                GameMaster.stopGameOverSound();
            }
        }
    }

    /**
     * Paints the Jframes background
     */
    private void paintBackground(Graphics2D g) {
        g.setColor(Color.yellow);
        g.fillRect(0, 0, width, height);
        paintGoal(g, 0, 150, height - 150, 150);
        paintGoal(g, GameMaster.getGoal_x(), GameMaster.getGoalWidth(), GameMaster.getGoalY(), GameMaster.getGoalWidth());
    }

    /**
     * Paints a specific area with black and white squares, resembles a goal flag pattern.
     *
     * @param xCoord  where you want the upper left corner to begin on the x axis
     * @param xLength how wide you want the area to be
     * @param yCoord  where you want the upper left corner to begin on the y axis
     * @param yLength how tall you want the area to be
     */
    private void paintGoal(Graphics2D g, int xCoord, int xLength, int yCoord, int yLength) {
        int squareSize = 15;
        for (int i = yCoord; i < yCoord + yLength; i += squareSize) {         // Y-led
            for (int j = xCoord; j < xCoord + xLength; j += squareSize) {             // X-led
                if ((i + j) / squareSize % 2 == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j, i, squareSize, squareSize);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(j, i, squareSize, squareSize);
                }
            }
        }
    }

    /**
     * Places out some random obstacles,
     * method will possible be deleted or replaced
     * as the obstacles are only colored figures in this method
     */
    private void paintObstacles(Graphics2D g) {
        for (List<Block> list : gameMaster.getLevel().getObstacles()) {
            for (Block block : list) {
                g.setColor(block.getColor());
                g.fillRect(block.getxCoord(), block.getyCoord(), block.getxLength(), block.getyLength());
            }
        }
    }

    /**
     * Paints the square resembling the player
     */
    private void paintPlayer(Graphics2D g) {
        g.setColor(Player.getPlayerColor());
        g.fillRect((int) Player.getPlayerXCoordinate(), (int) Player.getPlayerYCoordinate(), Player.getPlayerSize(), Player.getPlayerSize());
    }

    /**
     * Paints borders od game menu.
     */
    private void paintBordersOfTheMenu(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(GameMaster.GAME_WIDTH, 0, 5, GameMaster.GAME_HEIGHT);
        g.fillRect(GameMaster.GAME_WIDTH + GameMaster.GAMEMENU_WIDTH - 10, 0, 5, GameMaster.GAME_HEIGHT);
        g.fillRect(GameMaster.GAME_WIDTH, 0, GameMaster.GAMEMENU_WIDTH, 5);
        g.fillRect(GameMaster.GAME_WIDTH, GameMaster.GAME_HEIGHT - 5, GameMaster.GAMEMENU_WIDTH, 5);
    }

    /**
     * Paint the game menu.
     */
    private void paintMenu(Graphics2D g) {
        Font font = new Font("Verdana", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("Difficulty " + GameMaster.getGameDifficulty(), GameMaster.GAME_WIDTH + 75, GameMaster.GAME_HEIGHT - 300);
        Font font1 = new Font("Verdana", 0, 16);
        g.setFont(font1);
        g.drawString("Increase difficulty", GameMaster.GAME_WIDTH + 75, GameMaster.GAME_HEIGHT - 270);
        g.drawString("Decrease difficulty", GameMaster.GAME_WIDTH + 75, GameMaster.GAME_HEIGHT - 185);

        Font font2 = new Font("Arial", Font.ITALIC, 12);
        g.setFont(font2);
        g.drawString("SPACE", GameMaster.GAME_WIDTH + 125, GameMaster.GAME_HEIGHT - 250);
        //Button Space
        g.drawRect(GameMaster.GAME_WIDTH + 105, GameMaster.GAME_HEIGHT - 265, 80, 20);

        g.drawString("ENTER", GameMaster.GAME_WIDTH + 125, GameMaster.GAME_HEIGHT - 164);
        // Button enter
        g.fillRect(GameMaster.GAME_WIDTH + 120, GameMaster.GAME_HEIGHT - 180, 50, 2);
        g.fillRect(GameMaster.GAME_WIDTH + 120, GameMaster.GAME_HEIGHT - 180, 2, 20);
        g.fillRect(GameMaster.GAME_WIDTH + 120, GameMaster.GAME_HEIGHT - 180 + 20, 10, 2);
        g.fillRect(GameMaster.GAME_WIDTH + 120 + 48, GameMaster.GAME_HEIGHT - 180, 2, 42);
        g.fillRect(GameMaster.GAME_WIDTH + 120 + 10, GameMaster.GAME_HEIGHT - 180 + 40, 40, 2);
        g.fillRect(GameMaster.GAME_WIDTH + 120 + 10, GameMaster.GAME_HEIGHT - 180 + 20, 2, 22);
        // Arrow enter
        g.drawLine(GameMaster.GAME_WIDTH + 160, GameMaster.GAME_HEIGHT - 155, GameMaster.GAME_WIDTH + 160, GameMaster.GAME_HEIGHT - 150);
        g.drawLine(GameMaster.GAME_WIDTH + 142, GameMaster.GAME_HEIGHT - 150, GameMaster.GAME_WIDTH + 160, GameMaster.GAME_HEIGHT - 150);
        g.drawLine(GameMaster.GAME_WIDTH + 142, GameMaster.GAME_HEIGHT - 152, GameMaster.GAME_WIDTH + 142, GameMaster.GAME_HEIGHT - 148);
        g.drawLine(GameMaster.GAME_WIDTH + 142, GameMaster.GAME_HEIGHT - 152, GameMaster.GAME_WIDTH + 142, GameMaster.GAME_HEIGHT - 148);
        g.drawLine(GameMaster.GAME_WIDTH + 142, GameMaster.GAME_HEIGHT - 152, GameMaster.GAME_WIDTH + 138, GameMaster.GAME_HEIGHT - 150);
        g.drawLine(GameMaster.GAME_WIDTH + 142, GameMaster.GAME_HEIGHT - 148, GameMaster.GAME_WIDTH + 138, GameMaster.GAME_HEIGHT - 150);
    }

    /**
     * Paint the harts the represent number of player lifs left.
     */
    private void paintHearts(Graphics2D g) {
        for (int i = 0; i < Player.getLifeRemaining(); i++) {
            int x = GameMaster.GAME_WIDTH + 75 + i * 50;
            int y = GameMaster.GAME_HEIGHT - 100;
            Heart heart = new Heart(x, y);
            int[] xp = heart.getXp();
            int[] yp = heart.getYp();
            g.setColor(Color.RED);
            g.fillOval(x, y, 20, 20); //left circle
            g.fillOval(x + 20, y, 20, 20); //to cover middle spot
            g.fillOval(x + 13, y + 10, 10, 10); //right circle
            g.fillPolygon(xp, yp, xp.length); //bottom triangle
        }
    }

    /**
     * Paint "GAME OVER" if player loses the game.
     */
    private void paintGameOver(Graphics2D g) {
        Font font = new Font("Verdana", Font.BOLD, 100);
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString("GAME OVER", 200, GameMaster.GAME_HEIGHT / 2);
    }

    private void paintPlayAgain(Graphics2D g){
        Font font = new Font("Verdana", Font.BOLD, 60);
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString("PRESS A TO PLAY AGAIN", 120, GameMaster.GAME_HEIGHT / 2 + 200);
    }
    private void paintBlackBackground(Graphics2D g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
    }

    private void paintLevelNumber(Graphics2D g){
        g.setColor(Color.black);
        Font font1 = new Font("Verdana", Font.BOLD, 35);
        g.setFont(font1);
        g.drawString("LEVEL " + GameMaster.getGameLevel(), GameMaster.GAME_WIDTH + 75, GameMaster.GAME_HEIGHT - 350);
    }
}