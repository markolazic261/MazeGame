import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.FileChannel;

/**
 * Main class, call the GameMaster class to start the game
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            JFrame frame = new JFrame();
            frame.setSize(GameMaster.GAME_WIDTH + GameMaster.GAMEMENU_WIDTH, GameMaster.GAME_HEIGHT + 29);
            Level level1 = new Level(GameMaster.getGameLevel());
            GameMaster gameMaster = new GameMaster(level1);
            frame.setResizable(false);
            final GameFrame gameFrame = new GameFrame(gameMaster);
            frame.add(gameFrame);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            Sound.playSoundtrack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        gameFrame.invalidate();
                        gameFrame.repaint();
                        try {
                            Thread.sleep(16);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }).start();
            long lastTime = System.currentTimeMillis();
            while (true) {
                long newTime = System.currentTimeMillis();
                gameMaster.tick(newTime - lastTime, gameMaster.getLevel());
                lastTime = newTime;
                Thread.sleep(1);
            }
        }
    }
}
