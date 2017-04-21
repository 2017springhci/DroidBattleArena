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
    
    public boolean eval(Droid d, Program p) {
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
            case LESS_THAN:
                //Integers correspond to memory positions
                if(argument[0] instanceof Integer) {
                    //Compare to the number stored at index argument[0] in the NumericMemory array
                    if(argument[1] instanceof Integer) {
                        //NumericMemory[argument[0]] < NumericMemory[argument[1]]
                        return ((Number)p.getNumericMemory().get((Integer)argument[0])).doubleValue() <
                                ((Number)p.getNumericMemory().get((Integer)argument[1])).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case LESS_THAN_NUMERIC:
                if(argument[0] instanceof Integer) {
                    //Compare to the number stored at index argument[0] in the NumericMemory array
                    if(argument[1] instanceof Number) {
                        //NumericMemory[argument[0]] < argument[1]
                        return ((Number)p.getNumericMemory().get((Integer)argument[0])).doubleValue() <
                                ((Number) argument[1]).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case LESS_THAN_DOUBLE_NUMERIC:
                if(argument[0] instanceof Number) {
                    if(argument[1] instanceof Number) {
                        //argument[0] < argument[1]
                        return ((Number) argument[0]).doubleValue() < ((Number) argument[1]).doubleValue();
                    } else {
                        return false;
                    }
                } else {
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
                // CAN_MOVE(<DIRECTION>)
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
            case LESS_THAN_DOUBLE_NUMERIC:
                if (argument[0] instanceof Number) {
                    str = argument[0].toString();
                } else {
                    str = "??";
                }
                
                str += " < ";
                
                if (argument[1] instanceof Number) {
                    str += argument[1].toString();
                } else {
                    str += "??";
                }
                break;
            case LESS_THAN_NUMERIC:
                if (argument[0] instanceof Integer) {
                    str = "NumericMemory[" + ((Integer)argument[0]).toString() + "]";
                } else {
                    str = "??";
                }
                
                str += " < ";
                
                if (argument[1] instanceof Number) {
                    str += argument[1].toString();
                } else {
                    str += "??";
                }
                break;
            case LESS_THAN:
                // a < b
                if (argument[0] instanceof Integer) {
                    str = "NumericMemory[" + ((Integer)argument[0]).toString() + "]";
                } else {
                    str = "??";
                }
                
                str += " < ";
                
                if (argument[1] instanceof Integer) {
                    str += "NumericMemory[" + ((Integer)argument[1]).toString() + "]";
                } else {
                    str += "??";
                }
                break;
            default:
                str = "????";
        }
        return str;
    }
}
