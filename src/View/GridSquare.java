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
        color = colorSet(droidID);
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
    
    public static Color colorSet(int droidID) {
        switch (droidID) {
            case 0: return Color.BLUE;
            case 1: return Color.RED;
            case 2: return Color.GREEN;
            case 3: return Color.MAGENTA;
            default: return Color.GRAY;
        }
    }
    
    
}
