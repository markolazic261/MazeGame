import sun.audio.*;

import java.io.*;

public class Sound {
    private static AudioPlayer MGP;
    private static ContinuousAudioDataStream LOOP;
    /**
     * Plays the game soundtrack without stopping.
     */
    public static void playSoundtrack() {
        try {
            MGP = AudioPlayer.player;
            AudioStream BGM = new AudioStream(new FileInputStream("./sound.wav"));
            AudioData MD = BGM.getData();
            LOOP = new ContinuousAudioDataStream(MD);
        } catch (IOException error) {
            error.printStackTrace();
        }
        MGP.start(LOOP);
    }

    /**
     * Stop the game soundtrack.
     */
    public static void stopSoundtrack() {
        MGP.stop(LOOP);
    }

    /**
     * Plays game over sound.
     */
    public static void playGameOver() {
        try {
            InputStream in = new FileInputStream("./game_over.wav");
            AudioStream as = new AudioStream(in);
            AudioPlayer.player.start(as);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the life lost sound.
     */
    public static void playFailSound() {
        try {
            InputStream in = new FileInputStream("./life_lost.wav");
            AudioStream as = new AudioStream(in);
            AudioPlayer.player.start(as);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
