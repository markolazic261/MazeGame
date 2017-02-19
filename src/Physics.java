import java.util.Iterator;
import java.util.List;

/**
 * Class is responsible for all interaction between objects in game.
 * Created by Marko on 5/11/2016.
 */
public class Physics {

    /**
     * Simulate players collision with the walls. Doesn't let to the player to pass thought walls.
     *
     * @param level of the game
     */
    public static void collisionWithWalls(Level level) {
        Iterator<Block> it = level.getAllStaticWalls().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if (block.getColor() != Player.getPlayerColor()) {
                for (int i = 0; i < Player.getPlayerSize(); i += block.getxLength()) {
                    if (insideBlock(block, Player.getPlayerXCoordinate() + 1 + i, Player.getPlayerYCoordinate()) || insideBlock(block, Player.getPlayerXCoordinate() - 1 + Player.getPlayerSize(), Player.getPlayerYCoordinate())) {
                        Player.resetPlayerYupSpeed();
                    }
                    if (insideBlock(block, Player.getPlayerXCoordinate() + 1 + i, Player.getPlayerYCoordinate() + Player.getPlayerSize()) || insideBlock(block, Player.getPlayerXCoordinate() - 1 + Player.getPlayerSize(), Player.getPlayerYCoordinate() + Player.getPlayerSize())) {
                        Player.resetPlayerYdownSpeed();
                    }
                }
                for (int i = 0; i < Player.getPlayerSize(); i += block.getyLength()) {
                    if (insideBlock(block, Player.getPlayerXCoordinate(), Player.getPlayerYCoordinate() + 1 + i) || insideBlock(block, Player.getPlayerXCoordinate(), Player.getPlayerYCoordinate() + Player.getPlayerSize() - 1)) {
                        Player.resetPlayerXlSpeed();
                    }
                    if (insideBlock(block, Player.getPlayerXCoordinate() + Player.getPlayerSize(), Player.getPlayerYCoordinate() + 1 + i) || insideBlock(block, Player.getPlayerXCoordinate() + Player.getPlayerSize(), Player.getPlayerYCoordinate() - 1 + Player.getPlayerSize())) {
                        Player.resetPlayerXrSpeed();
                    }
                }
            } else if (intersectionWithPlayer(block)) {
                level.addTotDestroyedWalls(block);
                it.remove();
            }
        }
    }

    /**
     * Checks if a given coordinates are in an given block.
     *
     * @param block
     * @param x     coordinate
     * @param y     coordinate
     * @return true if inside, false otherwise
     */
    private static boolean insideBlock(Block block, double x, double y) {
        return (block.getxCoord() <= x && block.getyCoord() <= y &&
                block.getxCoord() + block.getxLength() >= x && block.getyCoord() + block.getyLength() >= y);
    }

    /**
     * Check if a given block is in the collision with a player.
     *
     * @param block
     * @return true if so, false otherwise
     */
    private static boolean intersectionWithPlayer(Block block) {
        double xmin = Math.max(block.getxCoord(), Player.getPlayerXCoordinate());
        double xmax1 = block.getxCoord() + block.getxLength();
        double xmax2 = Player.getPlayerXCoordinate() + Player.getPlayerSize();
        double xmax = Math.min(xmax1, xmax2);
        if (xmax > xmin) {
            double ymin = Math.max(block.getyCoord(), Player.getPlayerYCoordinate());
            double ymax1 = block.getyCoord() + block.getyLength();
            double ymax2 = Player.getPlayerYCoordinate() + Player.getPlayerSize();
            double ymax = Math.min(ymax1, ymax2);
            if (ymax > ymin) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if two blocks are in collision and give a coordinates of a intersection block.
     *
     * @param block1
     * @param block2
     * @param out
     * @return true if intersecting, false otherwise.
     */
    private static boolean intersectionBetweenBlocks(Block block1, Block block2, Block out) {
        double xmin = Math.max(block1.getxCoord(), block2.getxCoord());
        double xmax1 = block1.getxCoord() + block1.getxLength();
        double xmax2 = block2.getxCoord() + block2.getxLength();
        double xmax = Math.min(xmax1, xmax2);
        if (xmax > xmin) {
            double ymin = Math.max(block1.getyCoord(), block2.getyCoord());
            double ymax1 = block1.getyCoord() + block1.getyLength();
            double ymax2 = block2.getyCoord() + block2.getyLength();
            double ymax = Math.min(ymax1, ymax2);
            if (ymax > ymin) {
                out.addToXcoordinate(xmin);
                out.addToYcoordinate(ymin);
                out.addToXlength(xmax - xmin);
                out.addToYlength(ymax - ymin);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns block stucked in another block to the closest valid position.
     *
     * @param block
     * @param intersectionBlock
     */
    private static void returnBlockToValidPosition(Block block, Block intersectionBlock) {
        int xInter = intersectionBlock.getxLength();
        int yInter = intersectionBlock.getyLength();
        if (xInter > yInter) {
            if (block.getSpeed() > 0) {
                block.addToYcoordinate(-yInter);
            } else {
                block.addToYcoordinate(yInter);
            }
        } else if (xInter < yInter) {
            if (block.getSpeed() > 0) {
                block.addToXcoordinate(-xInter);
            } else {
                block.addToXcoordinate(xInter);
            }
        }
    }

    /**
     * Check if a given block is out of play bounds.
     *
     * @param block
     * @return true if so, false otherwise
     */
    private static boolean blockOutOfYBounds(Block block) {
        return (block.getyCoord() < 0 || block.getyCoord() + block.getyLength() > GameMaster.GAME_HEIGHT);
    }

    private static boolean blockOutOfXBounds(Block block) {
        return (block.getxCoord() < 0 || block.getxCoord() + block.getxLength() > GameMaster.GAME_WIDTH);
    }

    /**
     * Returns block stucked of out bounds to to closest valid position.
     *
     * @param block
     */
    private static void returnBlockToValidYPosition(Block block) {
        if (block.getSpeed() < 0) {
            block.addToYcoordinate(Math.abs(block.getyCoord()));
        }
        if (block.getSpeed() > 0) {
            block.addToYcoordinate(-(GameMaster.GAME_HEIGHT - block.getyCoord()));
        }
    }

    /**
     * Returns block that get out of X bounds to valid x position.
     *
     * @param block that is being moved.
     */
    private static void returnBlockToValidXPosition(Block block) {
        if (block.getSpeed() < 0) {
            block.addToXcoordinate(Math.abs(block.getxCoord()));
        }
        if (block.getSpeed() > 0) {
            block.addToXcoordinate(-(GameMaster.GAME_WIDTH - block.getxCoord()));
        }
    }

    /**
     * Simulate bouncing of the objects. Check if some object are stuck and then returns it to the closest valid position.
     *
     * @param level of the game
     * @param time  between previous bounce.
     */
    public static void verticalBouncingSquares(Level level, long time) {
        Block tempIntersectionBlock = new Block(0, 0, 0, 0);
        for (Block block : level.getAllVerticalMovingSquareObstacles()) {
            if (block.getSpeed() > 0)
                block.addToYcoordinate(time * block.getSpeed() + ((GameMaster.getGameDifficulty() - 1)) * 0.05);
            else block.addToYcoordinate(time * block.getSpeed() - ((GameMaster.getGameDifficulty() - 1)) * 0.05);
            if (blockOutOfYBounds(block)) {
                returnBlockToValidYPosition(block);
                block.bounce();
            }
            bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllStaticWalls());
            bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllHorizontalMovingWalls());
            bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllVerticalMovingWalls());

            if (intersectionWithPlayer(block)) {
                restartLevel(level);
            }
        }
    }

    /**
     * Simulates bouncing against walls
     *
     * @param block                 that is bouncing.
     * @param tempIntersectionBlock intersection block
     * @param list                  of blocks which other bounds off
     */
    private static void bouncingAgainstWalls(Block block, Block tempIntersectionBlock, List<Block> list) {
        for (Block movingWall : list) {
            if (intersectionBetweenBlocks(movingWall, block, tempIntersectionBlock)) {
                returnBlockToValidPosition(block, tempIntersectionBlock);
                block.bounce();
            }
        }
    }

    /**
     * Simulate vertical moving and bouncing of moving walls.
     *
     * @param level current level of the game.
     * @param time  between previous bounce.
     */
    public static void verticalBouncingWalls(Level level, long time) {
        if (level.getObstacles().size() > 3) {
            Block tempIntersectionBlock = new Block(0, 0, 0, 0);
            for (Block block : level.getAllVerticalMovingWalls()) {
                if (block.getSpeed() > 0)
                    block.addToYcoordinate(time * block.getSpeed() + ((GameMaster.getGameDifficulty() - 1)) * 0.05);
                else block.addToYcoordinate(time * block.getSpeed() - ((GameMaster.getGameDifficulty() - 1)) * 0.05);
                if (blockOutOfYBounds(block)) {
                    returnBlockToValidYPosition(block);
                    block.bounce();
                }
                bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllStaticWalls());
                if (intersectionWithPlayer(block)) {
                    restartLevel(level);
                }
            }
        }
    }

    /**
     * Simulates horizontal moving and bouncing of moving walls.
     *
     * @param level current level of the game.
     * @param time  between previous bounce.
     */
    public static void horizontalBouncingWalls(Level level, long time) {
        if (level.getObstacles().size() > 4) {
            Block tempIntersectionBlock = new Block(0, 0, 0, 0);
            for (Block block : level.getAllHorizontalMovingWalls()) {
                if (block.getSpeed() > 0)
                    block.addToXcoordinate(time * block.getSpeed() + ((GameMaster.getGameDifficulty() - 1)) * 0.05);
                else block.addToXcoordinate(time * block.getSpeed() - ((GameMaster.getGameDifficulty() - 1)) * 0.05);
                if (blockOutOfXBounds(block)) {
                    returnBlockToValidXPosition(block);
                    block.bounce();
                }
                bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllStaticWalls());
                if (intersectionWithPlayer(block)) {
                    restartLevel(level);
                }
            }
        }
    }

    /**
     * Simulate horizontal bouncing of moving square blocks.
     *
     * @param level current level of the game.
     * @param time  between previous bounce.
     */
    public static void horizontalBouncingSquares(Level level, long time) {
        if (level.getObstacles().size() > 3) {
            Block tempIntersectionBlock = new Block(0, 0, 0, 0);
            for (Block block : level.getAllHorizontalMovingSquareObstacles()) {
                if (block.getSpeed() > 0)
                    block.addToXcoordinate(time * block.getSpeed() + ((GameMaster.getGameDifficulty() - 1)) * 0.05);
                else block.addToXcoordinate(time * block.getSpeed() - ((GameMaster.getGameDifficulty() - 1)) * 0.05);
                if (blockOutOfXBounds(block)) {
                    returnBlockToValidXPosition(block);
                    block.bounce();
                }
                bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllStaticWalls());
                bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllHorizontalMovingWalls());
                bouncingAgainstWalls(block, tempIntersectionBlock, level.getAllVerticalMovingWalls());
                if (intersectionWithPlayer(block)) {
                    restartLevel(level);
                }
            }
        }
    }

    /**
     * Restart level. Decrease number of life left and restore players color, position to default.
     * Restore removed obstacles from that level.
     *
     * @param level current level of the game
     */
    private static void restartLevel(Level level) {
        Player.loseALife();
        Player.resetPlayerColor();
        returnPlayer();
        level.restoreRemovedObstacles();
        Sound s = new Sound();
        s.playFailSound();
    }

    /**
     * Returns player to its default position.
     */
    public static void returnPlayer() {
        Player.changePlayerXCoordinate(50);
        Player.changePlayerYCoordinate(GameMaster.GAME_HEIGHT - 100);
    }

    /**
     * Checks if player intersects with candy. If so, change player color to candy color, adds
     * candy to list of eaten candis and remove it from game.
     *
     * @param level current level if the game
     */
    public static void checkIfCandyIsTaken(Level level) {
        if (level.getObstacles().size() > 2) {
            Iterator<Block> it = level.getAllCandies().iterator();
            while (it.hasNext()) {
                Block candy = it.next();
                if (intersectionWithPlayer(candy)) {
                    Player.changePlayersColor(candy.getColor());
                    level.addToEatenCandies(candy);
                    it.remove();
                }
            }
        }
    }
}
