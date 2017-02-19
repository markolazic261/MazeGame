import java.awt.*;

/**
 * A class which creates creates blocks of different sizes, speed and color.
 */
public class Block {
    private static Color[] colors = {
            Color.black,
            Color.RED,
            Color.GREEN,
            Color.MAGENTA,
            Color.blue,
            Color.WHITE,
    };

    private double xCoord;
    private double xLength;
    private double yCoord;
    private double yLength;
    private double speed;
    private Color color;

    /**
     * Creates a block with 4 parameters.
     *
     * @param xCoord
     * @param xLength
     * @param yCoord
     * @param yLength
     */
    public Block(int xCoord, int xLength, int yCoord, int yLength) {
        this.xCoord = xCoord;
        this.xLength = xLength;
        this.yCoord = yCoord;
        this.yLength = yLength;
    }

    /**
     * Creates a block with 5 parameters.
     *
     * @param xCoord
     * @param xLength
     * @param yCoord
     * @param yLength
     * @param speed
     * @param color
     */
    public Block(double xCoord, double xLength, double yCoord, double yLength, double speed, int color) {
        this.xCoord = xCoord;
        this.xLength = xLength;
        this.yCoord = yCoord;
        this.yLength = yLength;
        this.speed = speed;
        this.color = colors[color];
    }

    /**
     * Change direction of the block
     */
    public void bounce() {
        speed = -speed;
    }

    /**
     * Add a value to blocks Y coordinate.
     *
     * @param add value to be added.
     */
    public void addToYcoordinate(double add) {
        yCoord += add;
    }

    /**
     * Add a value to blocks X coordinate.
     *
     * @param add value to be added.
     */
    public void addToXcoordinate(double add) {
        xCoord += add;
    }

    /**
     * Add a value to blocks width.
     *
     * @param add value to be added.
     */
    public void addToXlength(double add) {
        xLength += add;
    }

    /**
     * Add a value to blocks height
     *
     * @param add value to be added.
     */
    public void addToYlength(double add) {
        yLength += add;
    }

    /**
     * Return blocks current X coordinate.
     */
    public int getxCoord() {
        return (int) xCoord;
    }

    /**
     * Return blocks current width.
     */
    public int getxLength() {
        return (int) xLength;
    }

    /**
     * Return blocks current Y coordinate.
     */
    public int getyCoord() {
        return (int) yCoord;
    }

    /**
     * Return blocks current height.
     */
    public int getyLength() {
        return (int) yLength;
    }

    /**
     * Return blocks current speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Return blocks current color.
     */
    public Color getColor() {
        return color;
    }
}