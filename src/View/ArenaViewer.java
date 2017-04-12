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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

/**
 *
 * @author joseph
 */
public class ArenaViewer extends JPanel implements ArenaListener {
    
    final private Arena arena;
    final private ArrayList<ArrayList<GridSquare>> grid;
    private ArrayList<Integer[]> lines;
    private ArrayList<Integer[]> changedSquares;
    
    @Override
    public void arenaNotify() {
        ArrayList<Integer[]> moves = arena.getDroidMoves();
        ArrayList<Integer[]> lasers = arena.getLasers();
        for (Integer[] m : moves) {
            grid.get(m[0]).get(m[1]).clear();
            grid.get(m[2]).get(m[3]).update(m[4]);
        }
        lines = lasers;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        
        for (int i = 0; i < arena.getWidth(); ++i) {
            for (int j = 0; j < arena.getHeight(); ++j) {
                int cellX = 10 * i + 10;
                int cellY = 10 * j + 10;
                //System.out.println("At " + i + ", " + j);
                g.setColor(grid.get(i).get(j).getColor());
                g.fillRect(cellX, cellY, 10, 10);
            }
        }
        //vertical lines
        for (int i = 0; i <= arena.getWidth(); ++i) {
            g.setColor(Color.BLACK);
            g.drawLine(i*10 + 10, 10, i*10 + 10, arena.getHeight()*10+10);
        }
        //horizontal lines
        for (int j = 0; j <= arena.getHeight(); ++j) {
            g.setColor(Color.BLACK); 
            g.drawLine(10, j*10 + 10, arena.getWidth()*10+10, j*10 + 10);
        }
        
    }
        
    /*@Override
    public void repaint() {
        super.repaint();
        for (Integer[] m : changedSquares) {
            
            grid.get(m[0]).get(m[1]).clear();
            grid.get(m[2]).get(m[3]).update(m[4]);
        }
    }*/
    
    

    public ArenaViewer (Arena arena) {
        this.arena = arena;
        arena.addListener(this);
        grid = new ArrayList<> (arena.getWidth());
        for (int i = 0; i < arena.getWidth(); ++i) {
            grid.add(new ArrayList<> (arena.getHeight()));
            for (int j = 0; j < arena.getHeight(); ++j) {
                grid.get(i).add(new GridSquare());
            }
        }
        ArrayList<Droid> participants = arena.getParticipants();
        for (int i = 0; i < participants.size(); i++) {
            grid.get(participants.get(i).getPosX()).get(participants.get(i).getPosY()).update(1);
        }
        /*GridLayout gl = new GridLayout(arena.getWidth(), arena.getHeight());
        gl.setHgap(1);
        gl.setVgap(1);
        this.setLayout(gl);*/
        //Grid Layout unfortunately requires me to add a row before adding the next one, making logic
        //a bit clunky. Regardless:
        /*for (int i = 0; i < arena.getHeight(); ++i) {
            for (ArrayList<GridSquare> column : grid) {
                this.add(column.get(i));
                System.out.println("sup");
            }
        }*/
        System.out.println("Width, height are " + arena.getWidth() + ", " + arena.getHeight());
        this.setPreferredSize(new Dimension (500, 500));
       
        
    }
    
    
}
