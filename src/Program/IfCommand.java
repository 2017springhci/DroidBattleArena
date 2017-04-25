package Program;

import java.util.ArrayList;

public class IfCommand extends BlockCommand{
    ArrayList<Command> elseBlock;
    
    public IfCommand() {
        super();
        elseBlock = new ArrayList<>();
    }
    
    public IfCommand(Condition c) {
        super(c);
        elseBlock = new ArrayList<>();
    }
    
    public void addElseCommand(Command c) {
        elseBlock.add(c);
    }
    
    public String toString() {
        String str = "IF (" + cond.toString() + ") {\n" +
                stringInterior(codeBlock) + "}";
        if(!elseBlock.isEmpty()) {
            str += " ELSE {\n" + stringInterior(elseBlock) +"}";
        }
        return str;
    }
    
    public void execute(Program p) {
        //If the condition is true, add the code inside the if
        //statement to the call stack
        if(cond.eval(p.getDroid(), p)) {
            
            for(int i = codeBlock.size() - 1; i >= 0; i--) {
                p.getCallStack().push(codeBlock.get(i));
            }
            
        } else {
            for(int i = elseBlock.size() - 1; i >= 0; i--) {
                p.getCallStack().push(elseBlock.get(i));
            }
        }
    }
}
