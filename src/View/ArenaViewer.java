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
import java.awt.ComponentOrientation;
import java.awt.GridLayout;

/**
 *
 * @author joseph
 */
public class ArenaViewer extends JPanel implements ActionListener {
    
    final private Arena arena;
    final private ArrayList<ArrayList<PixelComponent>> grid;
    private ArrayList<Integer[]> lines;
    
    public void actionPerformed(ActionEvent e) {
        ArrayList<Integer[]> moves = arena.getDroidMoves();
        ArrayList<Integer[]> lasers = arena.getLasers();
        for (Integer[] m : moves) {
            grid.get(m[0]).get(m[1]).clear();
            grid.get(m[2]).get(m[3]).set(m[4]);
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
        this.setLayout(new GridLayout(arena.getWidth(), arena.getHeight()));
        //Grid Layout unfortunately requires me to add a row before adding the next one, making logic
        //a bit clunky. Regardless:
        for (int i = 0; i < arena.getHeight(); ++i) {
            for (ArrayList<PixelComponent> column : grid) {
                this.add(column.get(i));
            }
        }
        
    }
    
    
}
