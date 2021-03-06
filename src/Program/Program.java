package Program;

import Arena.Droid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Program implements Serializable{
    private ArrayList<Command> commands;
    private Stack<Command> callStack;
    private PositionRegister positionRegister;
    private HashMap<Integer, Number> numericMemory;
    private Droid myDroid;
    
    public Program() {
        commands = new ArrayList<>();
        callStack = new Stack<>();
        positionRegister = new PositionRegister();
        myDroid = null;
        numericMemory = new HashMap<>();
    }
    
    public void addCommand(Command c) {
        commands.add(c);
    }
    
    public void setDroid(Droid d) {
        myDroid = d;
    }
    
    public Droid getDroid() {
        return myDroid;
    }
    
    public ExternalCommand runProgram() {
        while(!callStack.empty()) {
            //This runs until it hits the end of the program or the first external command, then returns null or the external command, as appropriate
            Command c = callStack.pop();
            if(c instanceof ExternalCommand) {
                return (ExternalCommand) c;
            } else {
                c.execute(this);
            }
        }
        //Start from the top, if we ran out of commands (so if we only had an internal command on the stack, we'll still make a move
        if(callStack.empty()) {
            for(int i = commands.size() - 1; i >=0; i--) {
                callStack.push(commands.get(i));
            }
        }
        while(!callStack.empty()) {
            //This runs until it hits the end of the program or the first external command, then returns null or the external command, as appropriate
            Command c = callStack.pop();
            if(c instanceof ExternalCommand) {
                return (ExternalCommand) c;
            } else {
                c.execute(this);
            }
        }
        return null;
    }
    
    public void printProgram() {
        for(Command c : commands) {
            System.out.println(c.toString());
        }
    }
    
    public static void saveProgram(Program p, File f) {
        try {
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(p);
            objectOut.close();
            fileOut.close();
        } catch(IOException ioe) {
            //We failed to save the file
            System.out.println("Error! Save attempt for " + f.getAbsolutePath() + " failed!");
            ioe.printStackTrace();
        }
    }
    
    public static Program loadProgram(File f) {
        Program program = null;
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            program = (Program) objectIn.readObject();
            fileIn.close();
            objectIn.close();
        } catch(FileNotFoundException fnfe) {
            //We failed to find the file
            System.out.println("Error! File " + f.getAbsolutePath() + " not found!");
        } catch(ClassNotFoundException cnfe) {
            //We failed to find the class of the file
            System.out.println("Error! File " + f.getAbsolutePath() + " contains an object of an unknown class!");
        } catch(IOException ioe) {
            //We failed to load the file
            System.out.println("Error! Load attempt for " + f.getAbsolutePath() + " failed!");
        }
        return program;
    }
    
    public PositionRegister getPositionRegister() {
        return positionRegister;
    }
    
    public HashMap getNumericMemory() {
        return numericMemory;
    }
    
    protected Stack<Command> getCallStack() {
        return callStack;
    }
}
