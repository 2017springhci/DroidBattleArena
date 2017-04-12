/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author joseph
 */
public class GridSquare {
    private Color color;
    
    public void clear() {
        color = Color.GRAY;
    }
    public void update(int droidID) {
        //putting this succinctly for now, can be made more ugly to be functional later
        color = ((droidID == 0) ? Color.BLUE : Color.RED);
    }
    public GridSquare(Color color) {
        this.color = color;
    }
    
    public GridSquare() {
        this.color = Color.GRAY;
    }
    
    public Color getColor() {
        return color;
    }
    
    
    
}
