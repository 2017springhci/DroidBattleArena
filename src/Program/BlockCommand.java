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
        str = indent(str);
        return str;
    }
    
    private String indent(String str) {
        String newStr = "";
        while(!str.isEmpty()) {
            int next = str.indexOf("\n");
            newStr += "    " + str.substring(0, next) + "\n";
            str = str.substring(next + 1, str.length());
        }
        return newStr;
    }
    
    public void addCommand(Command c) {
        codeBlock.add(c);
    }
    
    public void setCondition(Condition c) {
        cond = c;
    }
}
