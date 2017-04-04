package Program;

public class MoveCommand extends ExternalCommand {
    MoveEnum move;
    
    public MoveCommand(MoveEnum move) {
        this.move = move;
    }
    
    public void execute(Program p) {
        //Move in the right direction
        switch(move) {
            case NORTH:
                System.out.println("NORTH");
                break;
            case EAST:
                System.out.println("EAST");
                break;
            case SOUTH:
                System.out.println("SOUTH");
                break;
            case WEST:
                System.out.println("WEST");
                break;
        }
    }
    
    @Override
    public String toString() {
        String cmd = "MOVE ";
        switch(move) {
            case NORTH:
                cmd += "NORTH";
                break;
            case EAST:
                cmd += "EAST";
                break;
            case SOUTH:
                cmd += "SOUTH";
                break;
            case WEST:
                cmd += "WEST";
                break;
            default:
                cmd += "????";
        }
        return cmd;
    }
}
