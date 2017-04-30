/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Program;

/**
 *
 * @author joeBarrett
 */
import static Program.MoveEnum.EAST;
import static Program.MoveEnum.NORTH;
import static Program.MoveEnum.SOUTH;
import static Program.MoveEnum.WEST;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.TransferHandler;


public class DragnDropFrame extends JFrame {

    JTextField field;
    JList commandList;
    JList conditionList;
    DefaultListModel model;
    SpinnerModel spinnerModel;
    String[] conditions = {"CAN_MOVE NORTH","CAN_MOVE SOUTH", "CAN_MOVE EAST","CAN_MOVE WEST","LESS_THAN","LESS_THAN_NUMERIC", "LESS_THAN_DOUBLE_NUMERIC", "GREATER_THAN", "GREATER_THAN_NUMERIC", "GREATER_THAN_DOUBLE_NUMERIC", "EQUAL_TO", "EQUAL_TO_NUMERIC", "EQUAL_TO_DOUBLE_NUMERIC", "CLOSER_THAN"};
    String[] Cmds = {"MOVE NORTH", "MOVE SOUTH", "MOVE EAST", "MOVE WEST", "SHOOT", "IF,COND,(,),{,}ENDIF,ELSE{,}", "WHILE,COND,(,),{,}ENDWHILE"};
    JButton done;
    JButton save;
    JButton clear;
    JSpinner firex;
    JSpinner firey;
    JSpinner condArg1;
    JSpinner condArg2;
    Program p;
    JFileChooser fc;
    JMenuBar menuBar;
    
    File f;
    
    
    public DragnDropFrame() {

        setTitle("Drag and Drop Code");

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
      //panel to hold the value for shooting.....
        JPanel firePanel = new JPanel(new GridLayout(2,2));
        JPanel listPanel = new JPanel(new BorderLayout());
        fc = new JFileChooser();
     fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
                
                
       
        firex = new JSpinner();
        firey = new JSpinner();
        
        
        firePanel.add(new JLabel("Shoot x value: "));
        firePanel.add(firex);
        firePanel.add(new JLabel("Shoot y value: "));
        firePanel.add(firey);

        condArg1 = new JSpinner();
        condArg2 = new JSpinner();
        
        
        
        JScrollPane pane = new JScrollPane();
        pane.setPreferredSize(new Dimension(300, 300));

        commandList = new JList(Cmds);
        conditionList = new JList(conditions);
        model = new DefaultListModel();
        JList list = new JList(model);
        save = new JButton("Save");
        clear = new JButton("Clear");
        save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                            //Program p = new Program();
                            int fcValue = fc.showOpenDialog(null);
				if(fcValue == fc.APPROVE_OPTION){
					f = fc.getSelectedFile();
					done.setEnabled(true);
				}
                            
                            createProgram();
                            
                        
                            }
                        
         });
        
        clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                            //Program p = new Program();
                           model.clear();
                        }
         });
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(save);
        buttonPanel.add(clear);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        done = new JButton("EXIT");
        done.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                            //Program p = new Program();
                            p.printProgram();
                            System.exit(0);
                            
                        
                            }
                        
         });
        

        list.setDropMode(DropMode.INSERT);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setTransferHandler(new ListHandler());
        
        list.addKeyListener(new KeyListener(){


			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode() == KeyEvent.VK_DELETE){
				
					int selectedIndex = list.getSelectedIndex();
					if (selectedIndex != -1) {
					    model.remove(selectedIndex);
					}

				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}

		});

       // field = new JTextField("");
        //field.setPreferredSize(new Dimension(150, 25));
        //field.setDragEnabled(true);
        commandList.setDragEnabled(true);
        conditionList.setDragEnabled(true);

//        panel.add(field);
        panel.add(commandList);
        panel.add(firePanel);
        panel.add(condArg1);
        panel.add(conditionList);
        panel.add(condArg2);
        
        pane.getViewport().add(list); 
        listPanel.add(pane, BorderLayout.CENTER);
        panel.add(listPanel);
        panel.add(done);
        
        add(panel);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public Program getProgram(){
        return p;
    }
    
    
    private void createProgram(){
        p = new Program();
        int i = 0;
        while(i < model.size()){
            String s = (String) model.get(i);
           //can no longer use switch statement becuase of if/else stuff. Will have to move to series of if statements....
           if(s.startsWith("IF")|| s.startsWith("WHILE")){
               
               if(s.startsWith("IF")){
                   IfCommand x = new IfCommand();
                   i +=3;
                   String[] cond = model.get(i).toString().split(" ");
                   if(cond.length == 3){
                        Integer[] arguments = {Integer.parseInt(cond[0]), Integer.parseInt(cond[2])};
                        x.setCondition(new Condition(ConditionEnum.valueOf(cond[1]),arguments));
                   }
                   else{
                       String[] arguments = {cond[1]};
                       x.setCondition(new Condition(ConditionEnum.valueOf(cond[0]), arguments));
                   }
                   i+=3;
                   while(!model.get(i).toString().startsWith("}")){
                       s= (String) model.get(i);
                        switch(s) {
                        case "MOVE NORTH":
                            x.addCommand(new MoveCommand(NORTH));
                            break;
                        case "MOVE EAST":
                            x.addCommand(new MoveCommand(EAST));
                            break;
                        case "MOVE WEST":
                            x.addCommand(new MoveCommand(WEST));
                            break;
                        case "MOVE SOUTH":
                            x.addCommand(new MoveCommand(SOUTH));
                            break;
                        default:
                            String[] sArray = s.split(" ");
                            x.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                                }
                       i++;
                   }
                   //to bypass else
                   i+=2;
                   while(!model.get(i).toString().startsWith("}")){
                       s= (String) model.get(i);
                        switch(s) {
                        case "MOVE NORTH":
                            x.addElseCommand(new MoveCommand(NORTH));
                            break;
                        case "MOVE EAST":
                            x.addElseCommand(new MoveCommand(EAST));
                            break;
                        case "MOVE WEST":
                            x.addElseCommand(new MoveCommand(WEST));
                            break;
                        case "MOVE SOUTH":
                            x.addElseCommand(new MoveCommand(SOUTH));
                            break;
                        default:
                            String[] sArray = s.split(" ");
                            x.addElseCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                             }
                       i++;
                   }
                   p.addCommand(x);
                   
                   
               }
               
               if(s.startsWith("WHILE")){
                   WhileCommand x = new WhileCommand();
                   i +=3;
                   String[] cond = model.get(i).toString().split(" ");
                   if(cond.length ==3){
                   Integer[] arguments = {Integer.parseInt(cond[0]), Integer.parseInt(cond[2])};
                   x.setCondition(new Condition(ConditionEnum.valueOf(cond[1]),arguments));
                   }
                   else{
                       String[] arguments = {cond[1]};
                       x.setCondition(new Condition(ConditionEnum.valueOf(cond[0]), arguments));
                   }
                   i+=3;
                   while(!model.get(i).toString().startsWith("}")){
                       s= (String) model.get(i);
                        switch(s) {
                        case "MOVE NORTH":
                            x.addCommand(new MoveCommand(NORTH));
                            break;
                        case "MOVE EAST":
                            x.addCommand(new MoveCommand(EAST));
                            break;
                        case "MOVE WEST":
                            x.addCommand(new MoveCommand(WEST));
                            break;
                        case "MOVE SOUTH":
                            x.addCommand(new MoveCommand(SOUTH));
                            break;
                        default:
                            String[] sArray = s.split(" ");
                            x.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                                }
                       i++;
                   }
                   
                   p.addCommand(x);
                   
                   
               }
               
          }
          else{
           
            switch(s) {
            case "MOVE NORTH":
                p.addCommand(new MoveCommand(NORTH));
                break;
            case "MOVE EAST":
                p.addCommand(new MoveCommand(EAST));
                break;
            case "MOVE WEST":
                p.addCommand(new MoveCommand(WEST));
                break;
            case "MOVE SOUTH":
                p.addCommand(new MoveCommand(SOUTH));
                break;
            default:
                String[] sArray = s.split(" ");
                p.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                    }
           }
           
            i++;
        }
                   Program.saveProgram(p, f);

    }
    


    private class ListHandler extends TransferHandler {
        public boolean canImport(TransferSupport support) {
             if (!support.isDrop()) {
                 return false;
             }

             return support.isDataFlavorSupported(DataFlavor.stringFlavor);
         }

         public boolean importData(TransferSupport support) {
             if (!canImport(support)) {
               return false;
             }

             Transferable transferable = support.getTransferable();
             String line;
             try {
                line = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                if(line.equals("SHOOT")){
                    line = line + " " + firex.getValue() + " " + firey.getValue();
                }
                if(line.startsWith("LESS_THAN") || line.startsWith("GREATER") || line.startsWith("EQUAL") || line.startsWith("CLOSER")){
                    line = condArg1.getValue() + " " + line + " " + condArg2.getValue();
                }
             } catch (Exception e) {
               return false;
             }

             JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
             int index = dl.getIndex();

             String[] data = line.split(",");
             for (String item: data) {
           
              if (!item.isEmpty())
                 model.add(index++, item.trim());
             }
             return true;
         }
    }

    public static void main(String[] args) {
        new DragnDropFrame();
        
    }
}
