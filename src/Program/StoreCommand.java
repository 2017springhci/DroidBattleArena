package Program;


public class StoreCommand extends InternalCommand{
    //Store a number at an index
    Number num;
    Integer index;
    
    
    public StoreCommand(Number n, Integer i) {
        num = n;
        index = i;
    }
    
    public String toString() {
        String str = "STORE " + num + " AT INDEX " + index;
        return str;
    }
    
    public void execute(Program p) {
        p.getNumericMemory().put(index, num);
    }
}
