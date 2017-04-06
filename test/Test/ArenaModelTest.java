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
        Droid d = new Droid(5, 5, p);
        a.addParticipant(d);
        a.runGame();
    }
}
