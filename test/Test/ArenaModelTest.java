package Test;

import Arena.Arena;
import Arena.Droid;
import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import static Program.MoveEnum.WEST;
import Program.Program;
import Program.ShootCommand;

public class ArenaModelTest {
    public static void main(String[] args) {
        Program p1 = new Program();
        p1.addCommand(new ShootCommand(4, 0));
        p1.addCommand(new ShootCommand(4, 0));
        p1.addCommand(new ShootCommand(4, 0));
        p1.printProgram();
        System.out.println();
        
        Program p2 = new Program();
        p2.addCommand(new MoveCommand(NORTH));
        p2.addCommand(new MoveCommand(EAST));
        p2.addCommand(new MoveCommand(WEST));
        p2.printProgram();
        System.out.println();
        
        Arena a = new Arena(20, 20);
        Droid d1 = new Droid(5, 5, p1);
        Droid d2 = new Droid(9, 8, p2);
        a.addParticipant(d1);
        a.addParticipant(d2);
        a.runGame();
    }
}
