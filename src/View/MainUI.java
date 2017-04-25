/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Arena.Arena;
import Arena.Droid;
import Program.Program;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author joseph
 */
public class MainUI extends JFrame {
    private ArenaViewer arenaviewer;
    private Arena arena;
    private JScrollPane commands;
    private JScrollPane code;
    private JMenuBar menu;
    private JMenu file;
    private JMenuItem loadPlayerProgram;
    private JMenuItem loadEnemyProgram;
    private JMenuItem createProgram;
    private JMenuItem loadLevel;
    private JPanel content;
    public static void main(String[] args ) {
        MainUI toRun = new MainUI();
        toRun.setVisible(true);
        
        
    }
    
    public MainUI () {
        super();
        arena = new Arena(60, 30);
        arena.addParticipant(new Droid (9, 9, new Program()));
        arena.addParticipant (new Droid (49, 19, new Program()));
        BorderLayout layout = new BorderLayout();
        content = new JPanel(layout);
        menu = new JMenuBar();
        file = new JMenu("File");
        loadPlayerProgram = new JMenuItem("Load player program");
        loadEnemyProgram = new JMenuItem("Load enemy program");
        createProgram = new JMenuItem("Create Program");
        file.add(loadPlayerProgram);
        loadPlayerProgram.addActionListener(e -> 
        {       JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(".dba"));
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Arena.getPlayer(0).setProgram(Program.loadProgram(fc.getSelectedFile()));
		}
                
        });
        loadEnemyProgram.addActionListener(e -> 
        {       JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(".dba"));
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Arena.getPlayer(1).setProgram(Program.loadProgram(fc.getSelectedFile()));
		}
                
        });
		//view.setFocus();
        file.add(saveProgram);
        file.add(createProgram);
        menu.add(file);
        level = new JMenu("Level");
        loadLevel = new JMenuItem("Select level");
        
        level.add(loadLevel);
        menu.add(level);
        content.add(menu,BorderLayout.PAGE_START);
        
        
        commands = new JScrollPane(); 
        code = new JScrollPane();
        content.setPreferredSize(new Dimension(1800, 675));
  
        this.add(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
        
        
    }
    
}
