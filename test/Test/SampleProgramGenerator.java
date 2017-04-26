package Test;

import Program.Condition;
import static Program.ConditionEnum.CAN_MOVE;
import static Program.ConditionEnum.CLOSER_THAN;
import Program.DoNothingCommand;
import Program.IfCommand;
import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import static Program.MoveEnum.SOUTH;
import static Program.MoveEnum.WEST;
import Program.Program;
import Program.SenseCommand;
import static Program.SenseEnum.NEAREST;
import Program.ShootCommand;
import Program.WhileCommand;
import java.io.File;


public class SampleProgramGenerator {
    public static void main(String args[]) {
        Program p1 = new Program();
        p1.addCommand(new MoveCommand(NORTH));
        p1.addCommand(new MoveCommand(EAST));
        p1.addCommand(new MoveCommand(SOUTH));
        p1.addCommand(new MoveCommand(WEST));
        
        File f = new File("BotBot.dba");
        System.out.println(f.getAbsolutePath());
        Program.saveProgram(p1, f);
        
        
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
        
        f = new File("ShootNear.dba");
        System.out.println(f.getAbsolutePath());
        Program.saveProgram(p2, f);
        
        
        Program p3 = new Program();
        p3.addCommand(new DoNothingCommand());
        
        f = new File("DoNothing.dba");
        System.out.println(f.getAbsolutePath());
        Program.saveProgram(p3, f);
        
        
        Program p4 = new Program();
        Object[] narg = {NORTH};
        Condition nc = new Condition(CAN_MOVE, narg);
        WhileCommand nwc = new WhileCommand(nc);
        nwc.addCommand(new MoveCommand(NORTH));
        p4.addCommand(nwc);
        
        Object[] earg = {EAST};
        Condition ec = new Condition(CAN_MOVE, earg);
        WhileCommand ewc = new WhileCommand(ec);
        ewc.addCommand(new MoveCommand(EAST));
        p4.addCommand(ewc);
        
        Object[] sarg = {SOUTH};
        Condition sc = new Condition(CAN_MOVE, sarg);
        WhileCommand swc = new WhileCommand(sc);
        swc.addCommand(new MoveCommand(SOUTH));
        p4.addCommand(swc);
        
        Object[] warg = {WEST};
        Condition wc = new Condition(CAN_MOVE, warg);
        WhileCommand wwc = new WhileCommand(wc);
        wwc.addCommand(new MoveCommand(WEST));
        p4.addCommand(wwc);
        
        f = new File("Orbiter.dba");
        System.err.println(f.getAbsolutePath());
        Program.saveProgram(p4, f);
    }
}
