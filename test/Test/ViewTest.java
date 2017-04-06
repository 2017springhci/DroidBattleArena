/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Arena.Arena;
import Arena.Droid;
import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import Program.Program;
import Program.ShootCommand;
import View.ArenaViewer;
import javax.swing.JFrame;

/**
 *
 * @author joseph
 */
public class ViewTest {
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
        ArenaViewer view = new ArenaViewer(a);
        JFrame frame = new JFrame();
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
   }
}
