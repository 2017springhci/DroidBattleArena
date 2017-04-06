package Arena;

import Program.ExternalCommand;
import Program.MoveCommand;
import Program.ShootCommand;
import java.util.ArrayList;
import Program.MoveEnum;

public class Arena {
    private int height; //The number of squares high the arena is
    private int width; //The number of squares wide the arena is
    private ArrayList<Droid> participants; //The droids in the arena
    private ArrayList<ArenaListener> listeners;
    private ArrayList<Integer[]> laserShots; //{startX, startY, endX, endY, droidIndex}
    private ArrayList<Integer[]> droidMoves; //{startX, startY, endX, endY, droidIndex}
    
    public Arena(int h, int w) {
        height = h;
        width = w;
        participants = new ArrayList<>();
        listeners = new ArrayList<>();
        droidMoves = new ArrayList<>();
        laserShots = new ArrayList<>();
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    public ArrayList<Droid> getParticipants() {
        //Return a clone of the object containing participants (to reduce the potential for exploits)
        return (ArrayList<Droid>)participants.clone();
    }
    
    public ArrayList<Integer[]> getLasers() {
        //Return a list containing all shots fired in the last step
        return laserShots;
    }
    
    public ArrayList<Integer[]> getDroidMoves() {
        //Return a list containing all moves made in the last step
        return droidMoves;
    }
    
    public void addParticipant(Droid d) {
        participants.add(d);
        notifyListeners();
    }
    
    @Deprecated
    //Deprecated due to not yet being safe, there may be a droid at that position
    //This may be re-added once this is resolved
    public void addParticipantAtRandomPosition(Droid d) {
        d.setPosX((int)Math.random()*width);
        d.setPosY((int)Math.random()*height);
        addParticipant(d);
    }
    
    private void notifyListeners() {
        for(ArenaListener l : listeners) {
            l.arenaNotify();
        }
    }
    
    public void addListener(ArenaListener al) {
        listeners.add(al);
    }
    
    public boolean gameOver() {
        //Is the game over?
        //If one or fewer droids remain, then yes
        return (howManyAlive() < 2);
    }
    
    public int howManyAlive() {
        int count = 0;
        for(Droid d : participants) {
            if(d.isAlive()) {
                count++;
            }
        }
        return count;
    }
    
    private void cleanupTurn() {
        //Reset lists, to be ready for the next turn
        laserShots = new ArrayList<>();
        droidMoves = new ArrayList<>();
    }
    
    public void runGame() {
        do {
            progressTime();
        } while (!gameOver());
        System.out.println("And it is over!");
    }
    
    public void progressTime() {
        //Collect all of the external commands
        //Execute all movement, then all shooting
        //Execute simultaneously; if two bots move to the same square, both should take damage and neither should take the square
        //But for now... just execute in order of spawning. This can be fixed later
        for(int i = 0; i < participants.size(); i++) {
            ExternalCommand cmd = participants.get(i).executeTurn();
            if(cmd instanceof ShootCommand) {
                fireShot(participants.get(i), (ShootCommand)cmd, i);
            } else if(cmd instanceof MoveCommand) {
                checkAndMakeMove(participants.get(i), (MoveCommand)cmd, i);
            }
        }
        
        printActions();
        
        //Notify Listeners and clean up
        notifyListeners();
        cleanupTurn();
    }
    
    private void printActions() {
        System.out.println("SHOTS:");
        for(Integer[] shot : laserShots) {
            System.out.println("Start X: " + shot[0] + "  Start Y: " + shot[1] +
                    "  End X: " + shot[2] + "  End Y: " + shot[3] + "  Shooter: " + shot[4]);
        }
        
        System.out.println("\nMOVES:");
        for(Integer[] shot : droidMoves) {
            System.out.println("Start X: " + shot[0] + "  Start Y: " + shot[1] +
                    "  End X: " + shot[2] + "  End Y: " + shot[3] + "  Mover: " + shot[4]);
        }
    }
    
    private void checkAndMakeMove(Droid d, MoveCommand cmd, int droidIndex) {
        //If we can move in the desired fashion, do so and record the move
        
        //First, find the position we want to move to
        int movX = d.getPosX();
        int movY = d.getPosY();
        switch(cmd.getMoveDirection()){
            case NORTH:
                movY--;
                break;
            case EAST:
                movX++;
                break;
            case SOUTH:
                movY++;
                break;
            case WEST:
                movX--;
                break;
            default:
                //You didn't really want to move, did you?
        }
        
        if(onScreen(movX, movY) && currentOccupant(movX, movY) == null) {
            Integer[] moveEntry = {d.getPosX(), d.getPosY(), movX, movY, droidIndex};
            droidMoves.add(moveEntry);
            d.setPosX(movX);
            d.setPosY(movY);
        } else {
            //Oops, you ran into something
        }
    }
    
    
    private void fireShot(Droid d, ShootCommand cmd, int droidIndex) {
        //Just graphics, for now
        
        //Find the position that we're shooting at
        int tarX = d.getPosX() + cmd.getXShift();
        int tarY = d.getPosY() + cmd.getYShift();
        
        //If it is on the screen (between 0 and height for y; between 0 and width for x) then add it to the list of things to display
        if(onScreen(tarX, tarY)) {
            Integer[] shotEntry = {d.getPosX(), d.getPosX(),
                tarX, tarY, droidIndex};
            laserShots.add(shotEntry);
        }
    }
    
    private boolean onScreen(int x, int y) {
        if(0 <= x && x < width) {
            if(0 <= y && y < height) {
                return true;
            }
        }
        return false;
    }
    
    private Droid currentOccupant(int x, int y) {
        for(Droid d : participants) {
            if(d.getPosX() == x) {
                if(d.getPosY() == y) {
                    return d;
                }
            }
        }
        return null;
    }
}
