package Test;

import Program.Condition;
import static Program.ConditionEnum.CLOSER_THAN;
import Program.IfCommand;
import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import Program.Program;
import Program.SenseCommand;
import static Program.SenseEnum.NEAREST;
import Program.ShootCommand;
import java.io.File;


public class ProgramSaveTest {
    public static void main(String[] args) {
        Program p2 = new Program();
        p2.addCommand(new SenseCommand(NEAREST, 0));
        p2.addCommand(new SenseCommand(NEAREST, 1));
        Object[] posIndArg = {0, 1};
        Condition near = new Condition(CLOSER_THAN, posIndArg);
        IfCommand ic2 = new IfCommand(near);
        ic2.addCommand(new ShootCommand(0, p2));
        ic2.addCommand(new ShootCommand(0, p2));
        ic2.addCommand(new ShootCommand(0, p2));
        ic2.addCommand(new ShootCommand(0, p2));
        ic2.addElseCommand(new ShootCommand(1, p2));
        ic2.addElseCommand(new ShootCommand(1, p2));
        ic2.addElseCommand(new ShootCommand(1, p2));
        ic2.addElseCommand(new ShootCommand(1, p2));
        p2.addCommand(ic2);
        
        File f = new File("SaveSystemTest.dba");
        System.out.println(f.getAbsolutePath());
        Program.saveProgram(p2, f);
    }
}
