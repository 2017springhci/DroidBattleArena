package Program;


public class WhileCommand extends BlockCommand{
    
    public WhileCommand() {
        super();
    }
    
    public WhileCommand(Condition c) {
        super(c);
    }
    
    public String toString() {
        String str = "WHILE (" + cond.toString() + ") {\n" +
                stringInterior() + "\n}";
        return str;
    }
    
    public void execute(Program p) {
        //If the condition is true, add the while statement, then the 
        //code inside the while statement to the call stack
        if(cond.eval(p.getDroid())) {
            
            p.getCallStack().push(this);
            for(int i = codeBlock.size() - 1; i >= 0; i--) {
                p.getCallStack().push(codeBlock.get(i));
            }
            
        }
    }
}