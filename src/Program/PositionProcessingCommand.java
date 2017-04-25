package Program;

import Arena.Arena;


public class PositionProcessingCommand extends InternalCommand{
    Integer[] pos;
    PositionProcessingEnum processingType;
    Object[] data;
    
    public PositionProcessingCommand(Integer[] p, PositionProcessingEnum pt, Object[] d) {
        pos = p;
        processingType = pt;
        data = d;
    }
    
    public String toString() {
        String str;
        switch(processingType) {
            case POSITION_IN_DIRECTION:
                str = "POSITION_IN_DIRECTION ";
                switch((MoveEnum) data[0]) {
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
                str += " FROM PositionalMemory[" + pos[0] + "]";
                break;
            default:
                str = "??????";
        }
        return str;
    }
    
    public void execute(Program p) {
        Arena a = p.getDroid().getArena();
        switch(processingType) {
            case POSITION_IN_DIRECTION:
                //What is the position in a certain direction from PositionalMemory[pos[0]] (store at pos[1])
                RelativePosition currentPos = p.getPositionRegister().getPosition(pos[0]);
                switch((MoveEnum) data[0]) {
                    case NORTH:
                        if(currentPos.getY() + p.getDroid().getPosY() > 0) {
                            p.getPositionRegister().setPosition(pos[1], new RelativePosition(currentPos.getX(), currentPos.getY()-1));
                        } else {
                            p.getPositionRegister().setPosition(pos[1], currentPos);
                        }
                    case EAST:
                        if(currentPos.getX() + p.getDroid().getPosX() < a.getWidth() - 1) {
                            p.getPositionRegister().setPosition(pos[1], new RelativePosition(currentPos.getX() + 1, currentPos.getY()));
                        } else {
                            p.getPositionRegister().setPosition(pos[1], currentPos);
                        }
                    case SOUTH:
                        if(currentPos.getY() + p.getDroid().getPosY() < a.getHeight() - 1) {
                            p.getPositionRegister().setPosition(pos[1], new RelativePosition(currentPos.getX(), currentPos.getY() + 1));
                        } else {
                            p.getPositionRegister().setPosition(pos[1], currentPos);
                        }
                    case WEST:
                        if(currentPos.getX() + p.getDroid().getPosX() > 0) {
                            p.getPositionRegister().setPosition(pos[1], new RelativePosition(currentPos.getX() - 1, currentPos.getY()));
                        } else {
                            p.getPositionRegister().setPosition(pos[1], currentPos);
                        }
                    default:
                        p.getPositionRegister().setPosition(pos[1], currentPos);
                }
            default:
                //Do nothing
        }
    }
}
