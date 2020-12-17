public class ColliderBox {
    public int width = 1;
    public int height = 1;
    public int x;
    public int y;

    public ColliderBox(int x, int y, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getUpColliderLine()
    {
        return this.y;
    }

    public int getDownColliderLine()
    {
        return this.y + this.height;
    }
    public int getLeftColliderLine()
    {
        return this.x;
    }

    public int getRightColliderLine()
    {
        return this.x + this.width;
    }


}
