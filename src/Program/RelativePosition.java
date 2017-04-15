package Program;

public class RelativePosition {
    private int x;
    private int y;
    
    public RelativePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public String toString() {
        return "X: " + x + "  Y: " + y;
    }
}
