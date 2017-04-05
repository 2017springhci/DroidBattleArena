/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Program.Arena;

/**
 *
 * @author joseph
 */
public class ArenaViewer extends Component implements ActionListener {
    
    private Arena arena;
    
    public void actionPerformed(ActionEvent e) {
        ArrayList<int> participants = arena.getParticipants();
        ArrayList<int> lasers = arena.getLasers()
    }
}
