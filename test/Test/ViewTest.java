/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Arena.Arena;
import Arena.Droid;
import Program.ArithmeticCommand;
import static Program.ArithmeticEnum.MULTIPLY;
import Program.Condition;
import static Program.ConditionEnum.CAN_MOVE;
import static Program.ConditionEnum.LESS_THAN_NUMERIC;
import Program.IfCommand;
import Program.MoveCommand;
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import static Program.MoveEnum.SOUTH;
import Program.Program;
import Program.SenseCommand;
import static Program.SenseEnum.NEAREST;
import Program.ShootCommand;
import Program.StoreCommand;
import Program.WhileCommand;
import View.ArenaViewer;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author joseph
 */
public class ViewTest {
   public static void main(String[] args) {
        Program p1 = new Program();
        
        Object[] narg = {NORTH};
        Condition nc = new Condition(CAN_MOVE, narg);
        WhileCommand wc = new WhileCommand(nc);
        wc.addCommand(new MoveCommand(NORTH));
        p1.addCommand(wc);
        
        Object[] earg = {EAST};
        Condition ec = new Condition(CAN_MOVE, earg);
        IfCommand ic = new IfCommand(ec);
        ic.addCommand(new MoveCommand(EAST));
        p1.addCommand(ic);
        
        Object[] numarg = {1, 5};
        p1.addCommand(new StoreCommand(1, 1));
        p1.addCommand(new StoreCommand(2, 2));
        Condition ltc = new Condition(LESS_THAN_NUMERIC, numarg);
        WhileCommand wc2 = new WhileCommand(ltc);
        wc2.addCommand(new MoveCommand(SOUTH));
        wc2.addCommand(new ArithmeticCommand(MULTIPLY, 1, 2, 1));
        p1.addCommand(wc2);
        
        p1.printProgram();
        System.out.println("\n");
        
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
