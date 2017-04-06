package Test;

import Arena.Arena;
import Arena.Droid;
import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import Program.Program;
import Program.ShootCommand;

public class ArenaModelTest {
    public static void main(String[] args) {
        Program p = new Program();
        p.addCommand(new ShootCommand(4, 3));
        p.addCommand(new MoveCommand(NORTH));
        p.addCommand(new MoveCommand(EAST));
        p.printProgram();
        Arena a = new Arena(10, 10);
        Droid d1 = new Droid(5, 5, p);
        Droid d2 = new Droid(5, 4, p);
        a.addParticipant(d1);
        a.addParticipant(d2);
        a.runGame();
    }
}
