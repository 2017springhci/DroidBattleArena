package Program;

public class ShootCommand extends ExternalCommand {
    private int xTarget; //The x shift from our current position that we are aiming for
    private int yTarget; //The y shift from out current position that we are aiming for
    
    public ShootCommand(int x, int y) {
        xTarget = x;
        yTarget = y;
    }
    
    public void execute(Program p) {
        //Do nothing, at the moment
        //This is currently bypassed
    }
    
    public String toString() {
        String str = ("SHOOT X: " + xTarget + " Y: " + yTarget);
        return str;
    }
    
    public int getXShift() {
        return xTarget;
    }
    
    public int getYShift() {
        return yTarget;
    }
}
