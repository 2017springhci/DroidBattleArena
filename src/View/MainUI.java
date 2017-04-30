/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Arena.Arena;
import Arena.Droid;
import Arena.LogListener;
import Program.DragnDropFrame;
import Program.Program;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author joseph
 */
public class MainUI extends JFrame {
    private final ArenaViewer arenaViewer;
    private final Arena arena;
    private final JMenuBar menu;
    private final JMenu file;
    private final JMenu play;
    private final JMenu help;
    private final JMenuItem instructions;
    private final JMenuItem battle;
    private final JMenuItem loadPlayerProgram;
    private final JMenuItem loadEnemyProgram;
    private final JMenuItem createProgram;
    private final JPanel content;
    private final JTextArea log;
    public static void main(String[] args ) {
        MainUI toRun = new MainUI();
        toRun.setVisible(true);
        
        
    }
    
    public MainUI() {
        super();
        arena = new Arena(30, 60);
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
		fc.setFileFilter(new FileNameExtensionFilter(null, "dba"));
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			arena.getParticipants().get(0).setProgram(Program.loadProgram(fc.getSelectedFile()));
		}
                
        });
        file.add(loadEnemyProgram);
        loadEnemyProgram.addActionListener(e -> 
        {       JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(null, "dba"));
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			arena.getParticipants().get(1).setProgram(Program.loadProgram(fc.getSelectedFile()));
		}
                
        });
		//view.setFocus();
        file.add(createProgram);
        createProgram.addActionListener(e ->
        {
            new DragnDropFrame();
        });
        menu.add(file);
        play = new JMenu("Play");
        battle = new JMenuItem("Battle");
        battle.addActionListener(e -> {
            menu.repaint();
            arena.runGame();
            
        });
        play.add(battle);
        menu.add(play);
        help = new JMenu("Help");
        instructions = new JMenuItem("Instructions");
        instructions.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this, "First, create programs for both players using the File menu." +
                    "\n Then, load your created programs into the blue (player) and "
                    +"red (enemy) robots, also using the File menu." +
                    "\n Finally, hit Battle under Play to begin the battle!" +
                    "\n The log below the screen will tell you of what happened.");
        });
        help.add(instructions);
        menu.add(help);
        content.add(menu,BorderLayout.PAGE_START);
        arenaViewer = new ArenaViewer(arena);
        arenaViewer.setPreferredSize(new Dimension(575, 315));
        content.add(arenaViewer);
        log = new ArenaLogger();
        log.append("Welcome to Droid Battle Arena! Load programs to get started.");
        log.append("\n");
        log.setEditable(false);
        arena.addLogListener((LogListener) log);
        log.setPreferredSize(new Dimension(625, 100));
        content.add(log, BorderLayout.SOUTH);
        this.add(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
        
        
    }
    
    
}
