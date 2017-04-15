package Program;

import Arena.Arena;
import Arena.Droid;

public class Condition {
    private static Arena arena;
    private ConditionEnum condition;
    private Object[] argument;
    
    
    public Condition(ConditionEnum ce, Object[] arg) {
        //Do something
        condition = ce;
        argument = arg;
    }
    
    public static void setArena(Arena a) {
        arena = a;
    }
    
    public boolean eval(Droid d) {
        switch(condition) {
            case CAN_MOVE:
                int movX;
                int movY;
                switch((MoveEnum) argument[0]) {
                    case NORTH:
                        movX = d.getPosX();
                        movY = d.getPosY() - 1;
                        return arena.onScreen(movX, movY) && arena.currentOccupant(movX, movY) == null;
                    case EAST:
                        movX = d.getPosX() + 1;
                        movY = d.getPosY();
                        return arena.onScreen(movX, movY) && arena.currentOccupant(movX, movY) == null;
                    case SOUTH:
                        movX = d.getPosX();
                        movY = d.getPosY() + 1;
                        return arena.onScreen(movX, movY) && arena.currentOccupant(movX, movY) == null;
                    case WEST:
                        movX = d.getPosX() - 1;
                        movY = d.getPosY();
                        return arena.onScreen(movX, movY) && arena.currentOccupant(movX, movY) == null;
                    default:
                        return false;
                }  
            default:
                return false;
        }
    }
    
    public String toString() {
        String str;
        switch(condition) {
            case CAN_MOVE:
                str = "CAN_MOVE(";
                switch((MoveEnum) argument[0]) {
                    case NORTH:
                        str += "NORTH";
                        break;
                    case EAST:
                        str += "EAST";
                        break;
                    case SOUTH:
                        str += "SOUTH";
                        break;
                    case WEST:
                        str += "WEST";
                        break;
                    default:
                        str += "????";
                }
                str += ")";
                break;
            default:
                str = "????";
        }
        return str;
    }
}
