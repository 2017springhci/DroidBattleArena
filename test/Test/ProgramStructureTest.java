package Test;

import Program.MoveCommand;
import Program.Program;
import static Program.MoveEnum.*;

public class ProgramStructureTest {
    public static void main(String[] args) {
        Program p = new Program();
        p.addCommand(new MoveCommand(NORTH));
        p.addCommand(new MoveCommand(EAST));
        p.runProgram();
        p.printProgram();
    }
}
