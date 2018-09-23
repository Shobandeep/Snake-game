package root;

/*
    SIMPLE CONTAINER FOR X,Y VALUES
 */

public class Block {
    private int x, y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Block copyBlock(Block other) {
        return new Block(other.x, other.y);
    }

    public boolean equals(Object b) {
        if(!(b instanceof Block))
            return false;
        Block other = (Block)b;
        return (this.x == other.x && this.y == other.y);
    }
}
