/**
 * Creates a Heart shape and generates coordinates necessary to draw then.
 * Created by Marko on 5/13/2016.
 */
public class Heart {
    private int[] xp = new int[3];
    private int[] yp = new int[3];

    /**
     * Creates heart shape at giving coordinates.
     * @param x coordinate of the object.
     * @param y coordinate of the object.
     */
    public Heart(int x, int y) {
        xp[0] = x;
        xp[1] = x + 41;
        xp[2] = x + 20;
        yp[0] = y + 14;
        yp[1] = y + 14;
        yp[2] = y + 30;
    }

    /**
     * Return array of X coordinates needed to draw a heart.
     */
    public int[] getXp() {
        return xp;
    }

    /**
     * Return array of Y coordinates needed to draw a heart.
     * @return
     */
    public int[] getYp() {
        return yp;
    }
}
