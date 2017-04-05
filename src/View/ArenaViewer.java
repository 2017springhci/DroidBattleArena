/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Arena.Arena;
import java.util.ArrayList;
import Arena.Droid;
import java.awt.GridLayout;

/**
 *
 * @author joseph
 */
public class ArenaViewer extends JPanel implements ActionListener {
    
    private Arena arena;
    private ArrayList<ArrayList<PixelComponent>> grid;
    private ArrayList<Integer[]> lines;
    
    public void actionPerformed(ActionEvent e) {
        ArrayList<Integer[]> moves = arena.getDroidMoves();
        ArrayList<Integer[]> lasers = arena.getLasers();
        for (Integer[] m : moves) {
            grid.get(m[0].intValue()).get(m[1].intValue()).clear();
            grid.get(m[2].intValue()).get(m[3].intValue()).set(m[4]);
        }
        lines = lasers;
    }
    
    public void paintComponent() {
        
    }
    
    public ArenaViewer (Arena arena) {
        this.arena = arena;
        grid = new ArrayList<ArrayList<PixelComponent>> (arena.getWidth());
        for (int i = 0; i < arena.getHeight(); ++i) {
            grid.add(new ArrayList<PixelComponent> (arena.getHeight()));
            for (int j = 0; j < arena.getWidth(); ++j) {
                grid.get(i).add(new PixelComponent());
            }
        }
        for (Droid d : arena.getParticipants()) {
            grid.get(d.getX()).get(d.getY()).update(d.getAlignment());
        }
        JPanel.setLayout(new GridLayout(arena.getWidth(), arena.getHeight()));
    }
    
    
}
