package Arena;

import java.util.ArrayList;

public class Arena {
    private int height; //The number of squares high the arena is
    private int width; //The number of squares wide the arena is
    private ArrayList<Droid> participants; //The droids in the arena
    
    public Arena(int h, int w) {
        height = h;
        width = w;
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
}
