package Program;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Program implements Serializable{
    private ArrayList<Command> commands;
    private Stack<Command> callStack;
    private PositionRegister positionRegister;
    
    public Program() {
        commands = new ArrayList<>();
        callStack = new Stack<>();
        positionRegister = new PositionRegister();
    }
    
    public void addCommand(Command c) {
        commands.add(c);
    }
    
    public ExternalCommand runProgram() {
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
                c.execute();
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
}
