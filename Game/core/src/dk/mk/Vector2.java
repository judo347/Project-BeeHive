package dk.mk;

public class Vector2 {

    public int x;
    public int y;

    public Vector2(Vector2 vector2){
        this(vector2.x, vector2.y);
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
