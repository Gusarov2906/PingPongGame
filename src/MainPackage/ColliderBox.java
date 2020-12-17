package MainPackage;

/**
 * Class ColliderBox - class with dimensions for tracking collision
 */

public class ColliderBox {
    public int width = 1;
    public int height = 1;
    public int x;
    public int y;

    /**
     * Constructor with parameters
     * @param x - coordinate x
     * @param y- coordinate y
     * @param width - width of box
     * @param height - height of box
     */

    public ColliderBox(int x, int y, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    /**
     * get Y for Up Collider Line
     * @return y
     */

    public int getUpColliderLine()
    {
        return this.y;
    }

    /**
     * get Y for Down Collider Line
     * @return y
     */
    public int getDownColliderLine()
    {
        return this.y + this.height;
    }

    /**
     * get X for Left Collider Line
     * @return x
     */
    public int getLeftColliderLine()
    {
        return this.x;
    }

    /**
     * get X for Left Collider Line
     * @return x
     */

    public int getRightColliderLine()
    {
        return this.x + this.width;
    }

}
