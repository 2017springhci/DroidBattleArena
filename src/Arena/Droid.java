package Arena;

import Program.ExternalCommand;
import Program.Program;

/**
 *
 * This class is for the simulation's representation of the bots
 * 
 */
public class Droid {
    private int posX; //The droid's x coordinate
    private int posY; //The droid's y coordinate
    private Program prog; //The droid's program
    
    public Droid(int x, int y, Program p) {
        posX = x;
        posY = y;
        prog = p;
    }

    /**
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    protected ExternalCommand executeTurn() {
        return prog.runProgram();
    }
    
    public boolean isAlive() {
        //Death isn't implemented yet
        return true;
    }
}
