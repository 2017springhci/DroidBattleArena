package Test;

import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import Program.Program;
import Program.ShootCommand;
import java.io.File;


public class ProgramSaveTest {
    public static void main(String[] args) {
        Program p = new Program();
        p.addCommand(new MoveCommand(NORTH));
        p.addCommand(new MoveCommand(EAST));
        p.addCommand(new ShootCommand(4, 3));
        p.runProgram();
        p.printProgram();
        
        File f = new File("SaveSystemTest.dba");
        System.out.println(f.getAbsolutePath());
        Program.saveProgram(p, f);
    }
}
