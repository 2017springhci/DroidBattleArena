package Program;

import java.util.ArrayList;


public abstract class BlockCommand extends InternalCommand{
    ArrayList<Command> codeBlock;
    Condition cond;
    
    public BlockCommand() {
        codeBlock = new ArrayList<>();
    }
    
    public BlockCommand(Condition c) {
        codeBlock = new ArrayList<>();
        cond = c;
    }
    
    public String stringInterior() {
        String str = "";
        for(Command c : codeBlock) {
            str += c.toString() + "\n";
        }
        return str;
    }
    
    public void addCommand(Command c) {
        codeBlock.add(c);
    }
    
    public void setCondition(Condition c) {
        cond = c;
    }
}
