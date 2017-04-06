/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import Arena.ArenaListener;
import Arena.Arena;
import java.util.ArrayList;
import Arena.Droid;
import java.awt.GridLayout;

/**
 *
 * @author joseph
 */
public class ArenaViewer extends JPanel implements ArenaListener {
    
    final private Arena arena;
    final private ArrayList<ArrayList<PixelComponent>> grid;
    private ArrayList<Integer[]> lines;
    
    @Override
    public void arenaNotify() {
        ArrayList<Integer[]> moves = arena.getDroidMoves();
        ArrayList<Integer[]> lasers = arena.getLasers();
        for (Integer[] m : moves) {
            grid.get(m[0]).get(m[1]).clear();
            grid.get(m[2]).get(m[3]).update(m[4]);
        }
        lines = lasers;
    }
    
    public void paintComponent() {
        
    }
    
    public ArenaViewer (Arena arena) {
        this.arena = arena;
        arena.addListener(this);
        grid = new ArrayList<ArrayList<PixelComponent>> (arena.getWidth());
        for (int i = 0; i < arena.getHeight(); ++i) {
            grid.add(new ArrayList<PixelComponent> (arena.getHeight()));
            for (int j = 0; j < arena.getWidth(); ++j) {
                grid.get(i).add(new PixelComponent());
            }
        }
        for (Droid d : arena.getParticipants()) {
            grid.get(d.getPosX()).get(d.getPosY()).update(d.getAlignment());
        }
        GridLayout gl = new GridLayout(arena.getWidth(), arena.getHeight());
        gl.setHgap(1);
        gl.setVgap(1);
        this.setLayout(gl);
        //Grid Layout unfortunately requires me to add a row before adding the next one, making logic
        //a bit clunky. Regardless:
        for (int i = 0; i < arena.getHeight(); ++i) {
            for (ArrayList<PixelComponent> column : grid) {
                this.add(column.get(i));
            }
        }
       
        
    }
    
    
}
