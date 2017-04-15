package Program;

public class ShootCommand extends ExternalCommand {
    private Integer xTarget; //The x shift from our current position that we are aiming for
    private Integer yTarget; //The y shift from out current position that we are aiming for
    private int registerIndex;
    private Program program;
    
    public ShootCommand(int x, int y) {
        xTarget = x;
        yTarget = y;
    }
    
    public ShootCommand(RelativePosition target) {
        xTarget = target.getX();
        yTarget = target.getY();
    }
    
    public ShootCommand(int ri, Program p) {
        xTarget = null;
        yTarget = null;
        registerIndex = ri;
        program = p;
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
        if(xTarget != null) {
            return xTarget;
        } else {
            return program.getPositionRegister().getPosition(registerIndex).getX();
        }
    }
    
    public int getYShift() {
        if(yTarget != null) {
            return yTarget;
        } else {
            return program.getPositionRegister().getPosition(registerIndex).getY();
        }
    }
}
