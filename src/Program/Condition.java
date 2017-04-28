package Program;

import Arena.Arena;
import Arena.Droid;
import java.io.Serializable;

public class Condition implements Serializable{
    private static Arena arena;
    private ConditionEnum condition;
    private Object[] argument;
    
    
    public Condition(ConditionEnum ce, Object[] arg) {
        //Do something
        condition = ce;
        argument = arg;
        if(condition == ConditionEnum.CAN_MOVE) {
            if(argument[0] instanceof String) {
                arg = new Object[1];
                switch((String)argument[0]) {
                    case("NORTH"):
                        arg[0] = (Object) MoveEnum.NORTH;
                        break;
                    case("EAST"):
                        arg[0] = (Object) MoveEnum.EAST;
                        break;
                    case("SOUTH"):
                        arg[0] = (Object) MoveEnum.SOUTH;
                        break;
                    case("WEST"):
                        arg[0] = (Object) MoveEnum.WEST;
                        break;
                }
                argument = arg;
            }
        }
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
            case GREATER_THAN:
                //Integers correspond to memory positions
                if(argument[0] instanceof Integer) {
                    //Compare to the number stored at index argument[0] in the NumericMemory array
                    if(argument[1] instanceof Integer) {
                        //NumericMemory[argument[0]] > NumericMemory[argument[1]]
                        return ((Number)p.getNumericMemory().get((Integer)argument[0])).doubleValue() >
                                ((Number)p.getNumericMemory().get((Integer)argument[1])).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case GREATER_THAN_NUMERIC:
                if(argument[0] instanceof Integer) {
                    //Compare to the number stored at index argument[0] in the NumericMemory array
                    if(argument[1] instanceof Number) {
                        //NumericMemory[argument[0]] > argument[1]
                        return ((Number)p.getNumericMemory().get((Integer)argument[0])).doubleValue() >
                                ((Number) argument[1]).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case GREATER_THAN_DOUBLE_NUMERIC:
                if(argument[0] instanceof Number) {
                    if(argument[1] instanceof Number) {
                        //argument[0] > argument[1]
                        return ((Number) argument[0]).doubleValue() > ((Number) argument[1]).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case EQUAL_TO:
                //Integers correspond to memory positions
                if(argument[0] instanceof Integer) {
                    //Compare to the number stored at index argument[0] in the NumericMemory array
                    if(argument[1] instanceof Integer) {
                        //NumericMemory[argument[0]] == NumericMemory[argument[1]]
                        return ((Number)p.getNumericMemory().get((Integer)argument[0])).doubleValue() ==
                                ((Number)p.getNumericMemory().get((Integer)argument[1])).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case EQUAL_TO_NUMERIC:
                if(argument[0] instanceof Integer) {
                    //Compare to the number stored at index argument[0] in the NumericMemory array
                    if(argument[1] instanceof Number) {
                        //NumericMemory[argument[0]] < argument[1]
                        return ((Number)p.getNumericMemory().get((Integer)argument[0])).doubleValue() ==
                                ((Number) argument[1]).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case EQUAL_TO_DOUBLE_NUMERIC:
                if(argument[0] instanceof Number) {
                    if(argument[1] instanceof Number) {
                        //argument[0] == argument[1]
                        return ((Number) argument[0]).doubleValue() == ((Number) argument[1]).doubleValue();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case CLOSER_THAN:
                if(argument[0] instanceof Integer) {
                    if(argument[1] instanceof Integer) {
                        //Is PositionMemory[argument[0]] closer than PositionMemory[argument[1]]
                        return (Math.pow(p.getPositionRegister().getPosition((Integer) argument[0]).getX(), 2) + 
                                Math.pow(p.getPositionRegister().getPosition((Integer) argument[0]).getY(), 2) <
                                Math.pow(p.getPositionRegister().getPosition((Integer) argument[1]).getX(), 2) +
                                Math.pow(p.getPositionRegister().getPosition((Integer) argument[1]).getY(), 2));
                    }
                    return false;
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
            case GREATER_THAN_DOUBLE_NUMERIC:
                if (argument[0] instanceof Number) {
                    str = argument[0].toString();
                } else {
                    str = "??";
                }
                
                str += " > ";
                
                if (argument[1] instanceof Number) {
                    str += argument[1].toString();
                } else {
                    str += "??";
                }
                break;
            case GREATER_THAN_NUMERIC:
                if (argument[0] instanceof Integer) {
                    str = "NumericMemory[" + ((Integer)argument[0]).toString() + "]";
                } else {
                    str = "??";
                }
                
                str += " > ";
                
                if (argument[1] instanceof Number) {
                    str += argument[1].toString();
                } else {
                    str += "??";
                }
                break;
            case GREATER_THAN:
                // a > b
                if (argument[0] instanceof Integer) {
                    str = "NumericMemory[" + ((Integer)argument[0]).toString() + "]";
                } else {
                    str = "??";
                }
                
                str += " > ";
                
                if (argument[1] instanceof Integer) {
                    str += "NumericMemory[" + ((Integer)argument[1]).toString() + "]";
                } else {
                    str += "??";
                }
                break;
            case EQUAL_TO_DOUBLE_NUMERIC:
                if (argument[0] instanceof Number) {
                    str = argument[0].toString();
                } else {
                    str = "??";
                }
                
                str += " == ";
                
                if (argument[1] instanceof Number) {
                    str += argument[1].toString();
                } else {
                    str += "??";
                }
                break;
            case EQUAL_TO_NUMERIC:
                if (argument[0] instanceof Integer) {
                    str = "NumericMemory[" + ((Integer)argument[0]).toString() + "]";
                } else {
                    str = "??";
                }
                
                str += " == ";
                
                if (argument[1] instanceof Number) {
                    str += argument[1].toString();
                } else {
                    str += "??";
                }
                break;
            case EQUAL_TO:
                // a == b
                if (argument[0] instanceof Integer) {
                    str = "NumericMemory[" + ((Integer)argument[0]).toString() + "]";
                } else {
                    str = "??";
                }
                
                str += " == ";
                
                if (argument[1] instanceof Integer) {
                    str += "NumericMemory[" + ((Integer)argument[1]).toString() + "]";
                } else {
                    str += "??";
                }
                break;
            case CLOSER_THAN:
                // a is closer than b
                if (argument[0] instanceof Integer) {
                    str = "PositionMemory[" + ((Integer)argument[0]).toString() + "]";
                } else {
                    str = "??";
                }
                
                str += " IS_CLOSER_THAN ";
                
                if (argument[1] instanceof Integer) {
                    str += "PositionMemory[" + ((Integer)argument[1]).toString() + "]";
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
