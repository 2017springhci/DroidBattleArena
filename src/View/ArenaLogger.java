/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Arena.LogListener;
import javax.swing.JTextArea;

/**
 *
 * @author joseph
 */
public class ArenaLogger extends JTextArea implements LogListener {
    public void logNotify(String s) {
        this.append(s);
    }
}
