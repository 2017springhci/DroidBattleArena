package Program;


public enum ConditionEnum {
    CAN_MOVE, //Can move in a given direction
    LESS_THAN, //Is the number in one memory position less than the number in another memory position?
    LESS_THAN_NUMERIC, //Is the number in one memory position less than some constant?
    LESS_THAN_DOUBLE_NUMERIC, //Is one constant less than another constant?
    GREATER_THAN,
    EQUAL_TO;
}
