import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Marko on 5/9/2016.
 * Class reader uses Scanner to scan level .txt files and create level obstacles.
 */
public class Reader {
    private static String[] textFiles = {
            "./level1.txt",
            "./level2.txt",
            "./level3.txt",
            "./level4.txt",
            "./level5.txt"
    };

    /**
     * Reads information about level from a .txt file that are written in specified pattern.
     *
     * @param level for which
     * @return a list of all obstacles
     */
    public static List read(int level) {
        List<List<Block>> allObsticles = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new FileReader(textFiles[level - 1]));
            int x = sc.nextInt();
            for (int i = 0; i < x; i++) {
                allObsticles.add(new ArrayList<>());
                int y = sc.nextInt();
                for (int m = 0; m < y; m++) {
                    allObsticles.get(i).add(new Block(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextInt()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allObsticles;
    }

    /**
     * Return a number of available levels.
     */
    public static int getNumberOfAvailableLevels() {
        return textFiles.length;
    }
}
