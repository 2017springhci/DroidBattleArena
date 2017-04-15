package Program;


public class SenseCommand extends ExternalCommand{
    private SenseEnum senseType;
    private int registerIndex;
    
    public SenseCommand(SenseEnum type, int rn) {
        senseType = type;
        registerIndex = rn;
    }
    
    public void execute() {
        //Do nothing, as this is bypassed at the moment
    }
    
    public String toString() {
        String cmd = "SENSE ";
        switch(senseType) {
            case NEAREST:
                cmd += "NEAREST";
                break;
            default:
                cmd += "????";
        }
        
        cmd += " REGISTER INDEX: " + registerIndex;
        
        return cmd;
    }
    
    public SenseEnum getSenseType() {
        return senseType;
    }
    
    public int getRegisterIndex() {
        return registerIndex;
    }
}
