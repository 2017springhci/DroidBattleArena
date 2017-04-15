package Program;

import java.util.ArrayList;


public class PositionRegister extends Register{
    private ArrayList<RelativePosition> myPositions;
    
    public PositionRegister() {
        myPositions = new ArrayList<>();
    }
    
    public void setPosition(int index, RelativePosition p) {
        myPositions.add(index, p);
    }
    
    public RelativePosition getPosition(int index) {
        while(myPositions.size() < index + 1) {
            myPositions.add(null);
        }
        if(myPositions.get(index) != null) {
            return myPositions.get(index);
        } else {
            setPosition(index, new RelativePosition(0, 0));
            return myPositions.get(index);
        }
    }
}
