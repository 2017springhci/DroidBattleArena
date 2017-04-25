/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author joseph
 */
public class MainUI extends JFrame {
    private ArenaViewer arena;
    private JScrollPane commands;
    private JScrollPane code;
    private JMenuBar menu;
    private JMenu file;
    private JMenu level;
    private JMenuItem saveProgram;
    private JMenuItem loadProgram;
    private JMenuItem loadLevel;
    private JPanel content;
    public static void main(String[] args ) {
        MainUI toRun = new MainUI();
        toRun.setVisible(true);
        
        
    }
    
    public MainUI () {
        super(); 
        BorderLayout layout = new BorderLayout();
        content = new JPanel(layout);
        menu = new JMenuBar();
        file = new JMenu("File");
        loadProgram = new JMenuItem("Load");
        saveProgram = new JMenuItem("Save");
        file.add(loadProgram);
        file.add(saveProgram);
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
