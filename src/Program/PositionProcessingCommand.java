package Program;


public class PositionProcessingCommand extends InternalCommand{
    RelativePosition[] pos;
    PositionProcessingEnum processingType;
    Object[] data;
    
    public PositionProcessingCommand(RelativePosition[] p, PositionProcessingEnum pt, Object[] d) {
        pos = p;
        processingType = pt;
        data = d;
    }
    
    public String toString() {
        switch(processingType) {
            default:
                return "";
        }
    }
    
    public void execute(Program p) {
        //do nothing
    }
}
