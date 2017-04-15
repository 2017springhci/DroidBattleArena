package Arena;

import Program.ExternalCommand;
import Program.Program;
import Program.RelativePosition;

/**
 *
 * This class is for the simulation's representation of the bots
 * 
 */
public class Droid {
    private static final int MAX_HEALTH = 1;
    
    private int posX; //The droid's x coordinate
    private int posY; //The droid's y coordinate
    private Program prog; //The droid's program
    private int health;
    
    public Droid(int x, int y, Program p) {
        posX = x;
        posY = y;
        prog = p;
        health = MAX_HEALTH;
        prog.setDroid(this);
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
        //It is dead if it has health <= 0
        return health > 0;
    }
    
    public void processHit() {
        //We've been shot! Decrease our health
        health--;
    }
    
    public void setPositionRegister(int ri, RelativePosition pos) {
        //Store some data in the Program's PositionRegister
        prog.getPositionRegister().setPosition(ri, pos);
    }
}
