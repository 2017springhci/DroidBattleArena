/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author joseph
 */
public class PixelComponent extends Component {
    private Color color;
    
    public void clear() {
        this.color = Color.gray;
    }
    public void update(int droidID) {
        //putting this succinctly for now, can be made more ugly to be functional later
        this.color = (droidID == 0) ? Color.BLUE : Color.RED;
    }
    public PixelComponent(Color color) {
        this.color = color;
    }
    
    public PixelComponent() {
        this.color = Color.gray;
    }
    
}
