package Program;

public class IfCommand extends BlockCommand{
    
    public IfCommand() {
        super();
    }
    
    public IfCommand(Condition c) {
        super(c);
    }
    
    public String toString() {
        String str = "IF (" + cond.toString() + ") {\n" +
                stringInterior() + "\n}";
        return str;
    }
    
    public void execute(Program p) {
        //If the condition is true, add the code inside the if
        //statement to the call stack
        if(cond.eval(p.getDroid(), p)) {
            
            for(int i = codeBlock.size() - 1; i >= 0; i--) {
                p.getCallStack().push(codeBlock.get(i));
            }
            
        }
    }
}
