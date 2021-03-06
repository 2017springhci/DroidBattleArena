package Arena;

import Program.Condition;
import Program.DoNothingCommand;
import Program.ExternalCommand;
import Program.MoveCommand;
import Program.RelativePosition;
import Program.ShootCommand;
import java.util.ArrayList;
import Program.SenseCommand;

public class Arena {
    private int height; //The number of squares high the arena is
    private int width; //The number of squares wide the arena is
    private ArrayList<Droid> participants; //The droids in the arena
    private ArrayList<ArenaListener> listeners;
    private ArrayList <LogListener> logListeners;
    private ArrayList<Integer[]> laserShots; //{startX, startY, endX, endY, droidIndex}
    private ArrayList<Integer[]> droidMoves; //{startX, startY, endX, endY, droidIndex}
    private ArrayList<Integer[]> droidDeaths; //{X, Y, droidIndex}
    private ArrayList<Droid> initialParticipants; //The original locations of the droids, to make resetting the arena easy
    
    public Arena(int h, int w) {
        height = h;
        width = w;
        participants = new ArrayList<>();
        listeners = new ArrayList<>();
        logListeners = new ArrayList<LogListener>();
        droidMoves = new ArrayList<>();
        laserShots = new ArrayList<>();
        droidDeaths = new ArrayList<>();
        initialParticipants = new ArrayList<Droid>();
        Condition.setArena(this);
    }
    
    public void reset() {
        participants = new ArrayList<>();
        for(Droid d : initialParticipants) {
            participants.add(new Droid(d.getPosX(), d.getPosY(), d.getProgram()));
        }
        droidMoves = new ArrayList<>();
        laserShots = new ArrayList<>();
        droidDeaths = new ArrayList<>();
        System.out.println("Resetting arena...\n");
        notifyLogListeners("Resetting arena...\n");
        notifyListeners();
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
        return participants;
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
        d.setArena(this);
        participants.add(d);
        //And save the initial position so that we can reset to it later
        initialParticipants.add(new Droid(d.getPosX(), d.getPosY(), d.getProgram()));
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
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            //do nothing
        }
        for(ArenaListener l : listeners) {
            l.arenaNotify();
            System.out.println("LISTENER NOTIFIED!");
        }
    }
    
    public void addListener(ArenaListener al) {
        listeners.add(al);
    }
    
    public void notifyLogListeners(String output) {
        for(LogListener ll : logListeners) {
            ll.logNotify(output);
        }
    }
    
    public void addLogListener(LogListener ll) {
        logListeners.add(ll);
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
        droidDeaths = new ArrayList<>();
    }
    
    public void runGame() {
        do {
            progressTime();
        } while (!gameOver());
        System.out.println("And it is over!");
        String output = "-----------------------\nGAME OVER!\n";
        //Output the result of the game
        for(int i = 0; i < participants.size(); i++) {
            if(participants.get(i).isAlive()) {
                output += "Player " + i + " wins!\n";
            }
        }
        if(!output.endsWith("wins!\n")) {
            output += "Draw!";
        }
        notifyLogListeners(output);
    }
    
    public void progressTime() {
        //Collect all of the external commands
        //Execute all movement, then all sensing, then all shooting
        //Execute simultaneously; if two bots move to the same square, both should take damage and neither should take the square
        //But for now... just execute in order of spawning. This can be fixed later
        
        //Move everything into lists first, so we can execute all movements at once, then all shots at once
        ArrayList<Integer> shootIndices = new ArrayList<>();
        ArrayList<ShootCommand> shootCommands = new ArrayList<>();
        ArrayList<Integer> senseIndices = new ArrayList<>();
        ArrayList<SenseCommand> senseCommands = new ArrayList<>();
        ArrayList<Integer> moveIndices = new ArrayList<>();
        ArrayList<MoveCommand> moveCommands = new ArrayList<>();
        
        for(int i = 0; i < participants.size(); i++) {
            if(!participants.get(i).isAlive()) {
                //Dead droids make no moves
                continue;
            }
            ExternalCommand cmd = participants.get(i).executeTurn();
            if(cmd instanceof ShootCommand) {
                shootIndices.add(i);
                shootCommands.add((ShootCommand) cmd);
            } else if(cmd instanceof MoveCommand) {
                moveIndices.add(i);
                moveCommands.add((MoveCommand) cmd);
            } else if(cmd instanceof SenseCommand) {
                senseIndices.add(i);
                senseCommands.add((SenseCommand)cmd);
            } else if(cmd instanceof DoNothingCommand) {
                //Do nothing
            }
        }
        
        for(int i = 0; i < moveIndices.size(); i++) {
            //Move, currently in order of placement into the arena
            checkAndMakeMove(participants.get(moveIndices.get(i)), moveCommands.get(i), moveIndices.get(i));
        }
        
        for(int i = 0; i < senseIndices.size(); i++) {
            //Sense, in order of placement into the arena (Note: order doesn't matter for this step)
            sense(participants.get(senseIndices.get(i)), senseCommands.get(i), senseIndices.get(i));
        }
        
        for(int i = 0; i < shootIndices.size(); i++) {
            //Shoot, in order of placement into the arena (Order also does not matter for this step)
            fireShot(participants.get(shootIndices.get(i)), shootCommands.get(i), shootIndices.get(i));
        }
        
        printActions();
        
        //Notify Listeners and clean up
        notifyListeners();
        cleanupTurn();
    }
    
    private void printActions() {
        String output = "";
        
        output += ("\nMOVES:\n");
        for(Integer[] shot : droidMoves) {
            output += "Player " + shot[4];
            int northSouth = shot[3] - shot[1];
            int eastWest = shot[2] - shot[0];
            if(northSouth == 0) {
                //The move was east, west, or bumped into a wall (and thus stayed still)
                if(eastWest > 0) {
                    //east
                    output += " Moved EAST";
                } else if (eastWest < 0) {
                    //west
                    output += " Moved WEST";
                } else {
                    //Stayed still
                    output += " Rammed a wall";
                }
            } else if (northSouth > 0) {
                output += " Moved SOUTH";
            } else {
                output += " Moved NORTH";
            }
            
            output += " [Start X: " + shot[0] + "  Start Y: " + shot[1] +
                    "  End X: " + shot[2] + "  End Y: " + shot[3] + "  Mover: " + shot[4] + "]\n";
        }
        
        
        output += "\nSHOTS:\n";
        for(Integer[] shot : laserShots) {
            output += "Player " + shot[4] + " has fired. " + ("[Start X: " + shot[0] + "  Start Y: " + shot[1] +
                    "  End X: " + shot[2] + "  End Y: " + shot[3] + "  Shooter: " + shot[4] + "]\n");
        }
        
        
        output += ("\nDEATHS:\n");
        for(Integer[] death : droidDeaths) {
            output += "Player " + death[2] + " has been defeated!" + (" [X: " + death[0] + "  Y: " + death[1] +
                    "  Deceased: " + death[2]+ "]\n");
        }
        output += ("\n\n\n");
        System.out.print(output);
        notifyLogListeners(output);
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
            Integer[] shotEntry = {d.getPosX(), d.getPosY(),
                tarX, tarY, droidIndex};
            laserShots.add(shotEntry);
        }
        
        for(int i = 0; i < participants.size(); i++) {
            Droid bot = participants.get(i);
            if(bot.getPosX() == tarX && bot.getPosY() == tarY && bot.isAlive()) {
                //Its a hit! Do damage!
                bot.processHit();
                if(!bot.isAlive()) {
                    //If it died, add it to the list of deaths, in case we want to render those
                    Integer[] deathEntry = {bot.getPosX(), bot.getPosY(), i};
                    droidDeaths.add(deathEntry);
                }
            }
        }
    }
    
    private void sense(Droid droid, SenseCommand cmd, int index) {
        //First, find our position. We may need it to evaluate the sense command.
        int x = droid.getPosX();
        int y = droid.getPosY();
        
        switch(cmd.getSenseType()) {
            case NEAREST:
                //First, find the position of the closest droid
                double bestDist = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2)) + 1;
                RelativePosition bestPos = null;
                for(Droid d : participants) {
                    if(!d.equals(droid)) {
                        double dist = Math.sqrt(Math.pow(x - d.getPosX(), 2) + Math.pow(y - d.getPosY(), 2));
                        if(dist < bestDist) {
                            bestDist = dist;
                            bestPos = new RelativePosition(d.getPosX() - x, d.getPosY() - y);
                        }
                    }
                }
                droid.setPositionRegister(cmd.getRegisterIndex(), bestPos);
                System.out.println("BESTPOS: " + bestPos);
                break;
            default:
                //Do nothing
        }
    }
    
    public boolean onScreen(int x, int y) {
        if(0 <= x && x < width) {
            if(0 <= y && y < height) {
                return true;
            }
        }
        return false;
    }
    
    public Droid currentOccupant(int x, int y) {
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
