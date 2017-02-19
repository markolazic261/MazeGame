import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marko on 5/9/2016.
 * Level class hold information about all levels. Field obstacles is a list which contains lists of all types
 * of obstacles we have.
 */
public class Level {

    private List<List<Block>> obstacles;
    private List<Block> eatenCandies = new ArrayList<>();
    private List<Block> destroyedWalls = new ArrayList<>();

    /**
     * Calls the read method from te Reader class and assigns value to the obstacles list.
     * Att the index 0 of the list will be a list of walls.
     * Att the index 1 of the list will be list of up and down moving square obstacles.
     * Att the index 2 of the list will be list of candies that change players color when taken.
     * Att the index 3 of the list will be list of right and left moving square obstacles.
     * Att the index 4 of the list will be list of moving walls.
     * Att the index 5 of the list will be list of moving walls.
     */
    public Level(int levelNumber) {
        obstacles = Reader.read(levelNumber);
    }

    /**
     * Returns 2D ArrayList with all obstacles.
     *
     * @return obstacles
     */
    public List<List<Block>> getObstacles() {
        return obstacles;
    }

    /**
     * Add a candy block to list of eaten candies.
     *
     * @param block to be added.
     */
    public void addToEatenCandies(Block block) {
        eatenCandies.add(block);
    }

    /**
     * Add a wall block to list of destroyed walls.
     *
     * @param block to be added.
     */
    public void addTotDestroyedWalls(Block block) {
        destroyedWalls.add(block);
    }

    /**
     * Restores all eaten candies and destroyed blocks if any exists.
     */
    public void restoreRemovedObstacles() {
        if (eatenCandies.size() > 0) obstacles.get(2).addAll(eatenCandies);
        if (destroyedWalls.size() > 0) obstacles.get(0).addAll(destroyedWalls);
    }

    /**
     * Return a list of all static walls.
     */
    public List<Block> getAllStaticWalls() {
        return obstacles.get(0);
    }

    /**
     * Return a list of all vertical moving squares.
     */
    public List<Block> getAllVerticalMovingSquareObstacles() {
        return obstacles.get(1);
    }

    /**
     * Return a list of all candies.
     */
    public List<Block> getAllCandies() {
        return obstacles.get(2);
    }

    /**
     * Return a list of all horizontal moving squares.
     */
    public List<Block> getAllHorizontalMovingSquareObstacles() {
        return obstacles.get(3);
    }

    /**
     * Return a list of all vertical moving walls.
     */
    public List<Block> getAllVerticalMovingWalls() {
        return obstacles.get(4);
    }

    /**
     * Return a list of all horizontal moving walls.
     */
    public List<Block> getAllHorizontalMovingWalls() {
        return obstacles.get(5);
    }
}
