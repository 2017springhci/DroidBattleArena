package Program;

import java.io.Serializable;

public abstract class Command implements Serializable{
    public abstract void execute();
    
    @Override
    public abstract String toString();
}
