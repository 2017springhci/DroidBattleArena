package Program;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Program implements Serializable{
    ArrayList<Command> commands;
    
    public Program() {
        commands = new ArrayList<>();
    }
    
    public void addCommand(Command c) {
        commands.add(c);
    }
    
    public void runProgram() {
        //while(true) {
            Stack<Command> callStack = new Stack<>();
            for(int i = commands.size() - 1; i >=0; i--) {
                callStack.push(commands.get(i));
            }
            while(!callStack.empty()) {
                callStack.pop().execute(this);
            }
        //}
    }
    
    public void printProgram() {
        for(Command c : commands) {
            System.out.println(c.toString());
        }
    }
}
