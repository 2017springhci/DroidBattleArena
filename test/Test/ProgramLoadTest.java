package Test;

import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import Program.Program;
import Program.ShootCommand;
import java.io.File;


public class ProgramLoadTest {
    public static void main(String[] args) {      
        File f = new File("SaveSystemTest.dba");
        System.out.println(f.getAbsolutePath());
        Program p = Program.loadProgram(f);
        
        p.runProgram();
        p.printProgram();
    }
}
