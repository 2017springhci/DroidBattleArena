/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

/**
 *
 * @author chris
 */
public class DoNothingCommand extends ExternalCommand{
    
    public DoNothingCommand() {
    }
    
    public String toString() {
        return "DO NOTHING";
    }
    
    public void execute(Program p) {
        //Do nothing
    }
}
