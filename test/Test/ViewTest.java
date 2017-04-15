/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Arena.Arena;
import Arena.Droid;
import Program.MoveCommand;
import static Program.MoveEnum.WEST;
import Program.Program;
import Program.SenseCommand;
import static Program.SenseEnum.NEAREST;
import Program.ShootCommand;
import View.ArenaViewer;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author joseph
 */
public class ViewTest {
   public static void main(String[] args) {
        Program p1 = Program.loadProgram(new File("BotBot.dba"));
        p1.printProgram();
        System.out.println();
        
        Program p2 = new Program();
        p2.addCommand(new SenseCommand(NEAREST, 0));
        p2.addCommand(new ShootCommand(0, p2));
        p2.addCommand(new ShootCommand(0, p2));
        p2.addCommand(new ShootCommand(0, p2));
        p2.addCommand(new ShootCommand(0, p2));
        p2.printProgram();
        System.out.println();
        
        Arena a = new Arena(20, 20);
        Droid d1 = new Droid(5, 5, p1);
        Droid d2 = new Droid(9, 8, p2);
        a.addParticipant(d1);
        a.addParticipant(d2);
        
        ArenaViewer view = new ArenaViewer(a);
        JFrame frame = new JFrame();
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.runGame();
   }
}
