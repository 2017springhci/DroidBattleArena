package Program;


public class ArithmeticCommand extends InternalCommand{
    ArithmeticEnum operation;
    int firstInputIndex;
    int secondInputIndex;
    int outputIndex;
    
    public ArithmeticCommand(ArithmeticEnum op, int fii, int sii, int oi) {
        operation = op;
        firstInputIndex = fii;
        secondInputIndex = sii;
        outputIndex = oi;
    }
    
    public String toString() {
        String str = "NumericMemory[" + outputIndex + "] <= NumericMemory[" + firstInputIndex + "] ";
        switch(operation) {
            case ADD:
                str += "+";
                break;
            case MULTIPLY:
                str += "*";
                break;
            case SUBTRACT:
                str += "-";
                break;
            case DIVIDE:
                str += "/";
                break;
            default:
                str += "?";
        }
        str += " NumericMemory[" + secondInputIndex + "]";
        return str;
    }
    
    public void execute(Program p) {
        switch(operation) {
            case ADD:
                p.getNumericMemory().put(outputIndex, ((Number)p.getNumericMemory().get(firstInputIndex)).doubleValue() +
                        ((Number)p.getNumericMemory().get(secondInputIndex)).doubleValue());
                break;
            case MULTIPLY:
                p.getNumericMemory().put(outputIndex, ((Number)p.getNumericMemory().get(firstInputIndex)).doubleValue() *
                        ((Number)p.getNumericMemory().get(secondInputIndex)).doubleValue());
                break;
            case SUBTRACT:
                p.getNumericMemory().put(outputIndex, ((Number)p.getNumericMemory().get(firstInputIndex)).doubleValue() -
                        ((Number)p.getNumericMemory().get(secondInputIndex)).doubleValue());
                break;
            case DIVIDE:
                try {
                    p.getNumericMemory().put(outputIndex, ((Number)p.getNumericMemory().get(firstInputIndex)).doubleValue() /
                            ((Number)p.getNumericMemory().get(secondInputIndex)).doubleValue());
                } catch(Exception e) {
                    //We must've divided by 0
                    //Initialize the position anyway
                    p.getNumericMemory().put(outputIndex, 0);
                }
                break;
            default:
                p.getNumericMemory().put(outputIndex, 0);
        }
    }
}
