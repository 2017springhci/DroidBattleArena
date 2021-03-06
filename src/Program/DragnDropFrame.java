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
import static java.awt.BorderLayout.CENTER;
import java.awt.Color;
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
import javax.swing.BoxLayout;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class DragnDropFrame extends JFrame {

    JTextField field;
    JList commandList;
    JList conditionList;
    DefaultListModel model;
    SpinnerModel spinnerModel;
    String[] conditions = {"CAN_MOVE NORTH","CAN_MOVE SOUTH", "CAN_MOVE EAST","CAN_MOVE WEST","LESS_THAN","LESS_THAN_NUMERIC", "LESS_THAN_DOUBLE_NUMERIC", "GREATER_THAN", "GREATER_THAN_NUMERIC", "GREATER_THAN_DOUBLE_NUMERIC", "EQUAL_TO", "EQUAL_TO_NUMERIC", "EQUAL_TO_DOUBLE_NUMERIC", "CLOSER_THAN"};
    String[] Cmds = {"MOVE_NORTH", "MOVE_SOUTH", "MOVE_EAST", "MOVE_WEST", "SENSE", "SHOOT","SHOOT_FROM_MEM", "STORE","ADD","SUBTRACT", "MULTIPLY","DIVIDE", "IF,COND,(,),{,}ENDIF,ELSE{,}", "WHILE,COND,(,),{,}ENDWHILE"};
    JButton done;
    JButton save;
    JButton clear;
    JSpinner firex;
    JSpinner firey;
    JSpinner firez;
    JSpinner condArg1;
    JSpinner condArg2;
    Program p;
    JFileChooser fc;
    JMenuBar menuBar;
    
    File f;
    
    
    public DragnDropFrame() {

        setTitle("Drag and Drop Code");

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
       // JPanel panel = new JPanel(new HorizontalLayout());
      //panel to hold the value for shooting.....
        JPanel firePanel = new JPanel(new GridLayout(3,2));
        JPanel listPanel = new JPanel(new BorderLayout());
        fc = new JFileChooser();
     fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmHelp = new JMenuItem("Help");
	mntmHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "This is your help, bitch!");
            }
            
        });
	mnFile.add(mntmHelp);
        
                
                
       
        firex = new JSpinner();
        firey = new JSpinner();
        firez = new JSpinner();
       firex.setVisible(false);
       firey.setVisible(false);
       firez.setVisible(false);
        JLabel xval = new JLabel(" ");
        JLabel yval = new JLabel(" ");
        JLabel zval = new JLabel(" ");
        firePanel.add(xval);
        firePanel.add(firex);
        firePanel.add(yval);
        firePanel.add(firey);
        firePanel.add(zval);
        firePanel.add(firez);

        condArg1 = new JSpinner();
        condArg2 = new JSpinner();
        
        JComponent editor1 = condArg1.getEditor();
        JFormattedTextField tf1 = ((JSpinner.DefaultEditor) editor1).getTextField();
        tf1.setColumns(2);
        
        
        JComponent editor2 = condArg2.getEditor();
        JFormattedTextField tf2 = ((JSpinner.DefaultEditor) editor2).getTextField();
        tf2.setColumns(2);
        
        condArg1.setVisible(false);
        condArg2.setVisible(false);
        JLabel condArg1Lab = new JLabel(" ");
        JLabel condArg2Lab = new JLabel(" ");
        JLabel summaryLabel = new JLabel(" ",SwingConstants.CENTER);
        
        JScrollPane pane = new JScrollPane();
        pane.setPreferredSize(new Dimension(300, 300));

        
        commandList = new JList(Cmds);
        conditionList = new JList(conditions);
        model = new DefaultListModel();
        JList list = new JList(model);
        save = new JButton("Save");
        clear = new JButton("Clear");
        
        
         conditionList.addListSelectionListener(new ListSelectionListener() {

           @Override
            public void valueChanged(ListSelectionEvent e) {
                if(conditionList.getSelectedValue().toString().startsWith("CAN_MOVE")){
                    //set bot labels(need to make), and spinners to not visible
                    condArg1Lab.setText(" ");
                    condArg2Lab.setText(" ");
                    condArg1.setVisible(false);
                    condArg2.setVisible(false);
                    summaryLabel.setText("CAN_MOVE");
                    
                }
                if(conditionList.getSelectedValue().equals("LESS_THAN") || conditionList.getSelectedValue().equals("GREATER_THAN") || conditionList.getSelectedValue().equals("EQUAL_TO")){
                    //set bot labels(need to make), and spinners to not visible
                    condArg1Lab.setText("Value: ");
                    condArg2Lab.setText("Value: ");
                    condArg1.setVisible(true);
                    condArg2.setVisible(true);
                    if(conditionList.getSelectedValue().equals("LESS_THAN")){
                        summaryLabel.setText(condArg1.getValue().toString() + " < " + condArg2.getValue().toString());
                    }
                    if(conditionList.getSelectedValue().equals("GREATER_THAN")){
                        summaryLabel.setText(condArg1.getValue().toString() + " > " + condArg2.getValue().toString());
                    }
                    if(conditionList.getSelectedValue().equals("EQUAL_TO")){
                        summaryLabel.setText(condArg1.getValue().toString() + " == " + condArg2.getValue().toString());
                    }
                    
                }
                if(conditionList.getSelectedValue().equals("LESS_THAN_NUMERIC") || conditionList.getSelectedValue().equals("GREATER_THAN_NUMERIC") || conditionList.getSelectedValue().equals("EQUAL_TO_NUMERIC")){
                    
                    condArg1Lab.setText("Location: ");
                    condArg2Lab.setText("Value: ");
                    condArg1.setVisible(true);
                    condArg2.setVisible(true);
                    if(conditionList.getSelectedValue().equals("LESS_THAN_NUMERIC")){
                        summaryLabel.setText("MEM[" + condArg1.getValue().toString() + "] < " + condArg2.getValue().toString());
                    }
                    if(conditionList.getSelectedValue().equals("GREATER_THAN_NUMERIC")){
                        summaryLabel.setText("MEM[" + condArg1.getValue().toString() + "] > " + condArg2.getValue().toString());
                    }
                    if(conditionList.getSelectedValue().equals("EQUAL_TO_NUMERIC")){
                        summaryLabel.setText("MEM[" + condArg1.getValue().toString() + "] == " + condArg2.getValue().toString());
                    }
                    
                }
                if(conditionList.getSelectedValue().equals("LESS_THAN_DOUBLE_NUMERIC") || conditionList.getSelectedValue().equals("GREATER_THAN_DOUBLE_NUMERIC") || conditionList.getSelectedValue().equals("EQUAL_TO_DOUBLE_NUMERIC") || conditionList.getSelectedValue().equals("CLOSER_THAN")){
                    
                    condArg1Lab.setText("Location: ");
                    condArg2Lab.setText("Location: ");
                    condArg1.setVisible(true);
                    condArg2.setVisible(true);
                    if(conditionList.getSelectedValue().equals("LESS_THAN_DOUBLE_NUMERIC")){
                        summaryLabel.setText("MEM[" + condArg1.getValue().toString() + "] < MEM[" + condArg2.getValue().toString()+ "]");
                    }
                    if(conditionList.getSelectedValue().equals("GREATER_THAN_DOUBLE_NUMERIC")){
                        summaryLabel.setText("MEM[" + condArg1.getValue().toString() + "] > MEM[" + condArg2.getValue().toString()+ "]");
                    }
                    if(conditionList.getSelectedValue().equals("EQUAL_TO_DOUBLE_NUMERIC")){
                        summaryLabel.setText("MEM[" + condArg1.getValue().toString() + "] == MEM[" + condArg2.getValue().toString()+ "]");
                    }
                    
                }
                
            pack();
            }
        });
        
        commandList.addListSelectionListener(new ListSelectionListener() {

           @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if(commandList.getSelectedValue().equals("STORE")){
                    xval.setText("Value: ");
                    yval.setText("Location: ");
                    zval.setText("");
                    firex.setVisible(true);
                    firey.setVisible(true);
                    firez.setVisible(false);
                    
                
                }
               else if(commandList.getSelectedValue().equals("SHOOT")){
                    xval.setText("Shoot x: ");
                    yval.setText("Shoot y: ");
                    zval.setText("");
                    firex.setVisible(true);
                    firey.setVisible(true);
                    firez.setVisible(false);
                }
               else if(commandList.getSelectedValue().equals("SHOOT_FROM_MEM")){
                   xval.setText("Mem. location: ");
                   yval.setText("");
                   zval.setText("");
                   firex.setVisible(true);
                   firey.setVisible(false);
                   firez.setVisible(false);
               }
               else if(commandList.getSelectedValue().equals("SENSE")){
                   xval.setText("Reg. Index");
                   yval.setText("");
                   zval.setText("");
                   firex.setVisible(true);
                   firey.setVisible(false);
                   firez.setVisible(false);
               }
               
               else if(commandList.getSelectedValue().equals("ADD") || commandList.getSelectedValue().equals("SUBTRACT") || commandList.getSelectedValue().equals("MULTIPLY") || commandList.getSelectedValue().equals("DIVIDE")){
                  xval.setText("First Index: ");
                  yval.setText("Second Index: ");
                  zval.setText("Output Index: ");
                  firex.setVisible(true);
                  firey.setVisible(true);
                  firez.setVisible(true);
               }
                else{
                    xval.setText("");
                    yval.setText("");
                    zval.setText("");
                    firex.setVisible(false);
                    firey.setVisible(false);
                    firez.setVisible(false);
                }
                pack();
            }
            
        });
        
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
        
        done = new JButton("EXIT");
        done.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                            //Program p = new Program();
                            try{
                                p.printProgram();

                            }
                            catch(NullPointerException x){
                            //do nothing
                            }
                                
                            //System.exit(0);
                            setVisible(false);
                            
                        
                            }
                        
         });
        
        buttonPanel.add(done);

        listPanel.add(buttonPanel, BorderLayout.SOUTH);

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
        JPanel cmdpanel = new JPanel();
        cmdpanel.add(commandList);
        cmdpanel.add(firePanel);
        cmdpanel.setBorder(new LineBorder(Color.BLACK));

        panel.add(cmdpanel);
        
        
        
       
        
        
        JPanel condpanel = new JPanel();
      

        condpanel.add(condArg1Lab);
        condpanel.add(condArg1);

        
        condpanel.add(conditionList);
       // condpanel.add(summaryLabel,BorderLayout.SOUTH);
        
       
        condpanel.add(condArg2Lab);
        condpanel.add(condArg2);
       
       
       condpanel.setBorder(new LineBorder(Color.BLACK));
         panel.add(condpanel);
        
        pane.getViewport().add(list); 
        listPanel.add(pane, BorderLayout.CENTER);
        panel.add(listPanel);
        
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
                        String[] sArray = s.split(" ");

            switch(sArray[0]) {
            case "MOVE_NORTH":
                x.addCommand(new MoveCommand(NORTH));
                break;
            case "MOVE_EAST":
                x.addCommand(new MoveCommand(EAST));
                break;
            case "MOVE_WEST":
                x.addCommand(new MoveCommand(WEST));
                break;
            case "MOVE_SOUTH":
                x.addCommand(new MoveCommand(SOUTH));
                break;
            case "SHOOT":
                 x.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                break;
            case "STORE":
                 x.addCommand(new StoreCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                 break;
            case "ADD":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.ADD, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SUBTRACT":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.SUBTRACT, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "MULTIPLY":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.MULTIPLY, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "DIVIDE":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.DIVIDE, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SHOOT_FROM_MEM":
                x.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), p));
                break;
            case "SENSE":
                x.addCommand(new SenseCommand(SenseEnum.NEAREST, Integer.parseInt(sArray[1])));
                break;
            default:
                
                    }
                       i++;
                   }
                   //to bypass else
                   i+=2;
                   while(!model.get(i).toString().startsWith("}")){
                       s= (String) model.get(i);
                        String[] sArray = s.split(" ");

            switch(sArray[0]) {
            case "MOVE_NORTH":
                x.addElseCommand(new MoveCommand(NORTH));
                break;
            case "MOVE_EAST":
                x.addElseCommand(new MoveCommand(EAST));
                break;
            case "MOVE_WEST":
                x.addElseCommand(new MoveCommand(WEST));
                break;
            case "MOVE_SOUTH":
                x.addElseCommand(new MoveCommand(SOUTH));
                break;
            case "SHOOT":
                x.addElseCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                break;
            case "STORE":
                x.addElseCommand(new StoreCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                 break;
            case "ADD":
                x.addElseCommand(new ArithmeticCommand(ArithmeticEnum.ADD, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SUBTRACT":
                x.addElseCommand(new ArithmeticCommand(ArithmeticEnum.SUBTRACT, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "MULTIPLY":
                x.addElseCommand(new ArithmeticCommand(ArithmeticEnum.MULTIPLY, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "DIVIDE":
                x.addElseCommand(new ArithmeticCommand(ArithmeticEnum.DIVIDE, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SHOOT_FROM_MEM":
                x.addElseCommand(new ShootCommand(Integer.parseInt(sArray[1]), p));
                break;
            case "SENSE":
                x.addElseCommand(new SenseCommand(SenseEnum.NEAREST, Integer.parseInt(sArray[1])));
                break;
            default:
                
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
                        String[] sArray = s.split(" ");

            switch(sArray[0]) {
            case "MOVE_NORTH":
                x.addCommand(new MoveCommand(NORTH));
                break;
            case "MOVE_EAST":
                x.addCommand(new MoveCommand(EAST));
                break;
            case "MOVE_WEST":
                x.addCommand(new MoveCommand(WEST));
                break;
            case "MOVE_SOUTH":
                x.addCommand(new MoveCommand(SOUTH));
                break;
            case "SHOOT":
                x.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                break;
            case "STORE":
                x.addCommand(new StoreCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                 break;
            case "ADD":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.ADD, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SUBTRACT":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.SUBTRACT, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "MULTIPLY":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.MULTIPLY, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "DIVIDE":
                x.addCommand(new ArithmeticCommand(ArithmeticEnum.DIVIDE, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SHOOT_FROM_MEM":
                x.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), p));
                break;
            case "SENSE":
                x.addCommand(new SenseCommand(SenseEnum.NEAREST, Integer.parseInt(sArray[1])));
                break;
            default:
                
                    }
                       i++;
                   }
                   
                   p.addCommand(x);
                   
                   
               }
               
          }
          else{
            String[] sArray = s.split(" ");

            switch(sArray[0]) {
            case "MOVE_NORTH":
                p.addCommand(new MoveCommand(NORTH));
                break;
            case "MOVE_EAST":
                p.addCommand(new MoveCommand(EAST));
                break;
            case "MOVE_WEST":
                p.addCommand(new MoveCommand(WEST));
                break;
            case "MOVE_SOUTH":
                p.addCommand(new MoveCommand(SOUTH));
                break;
            case "SHOOT":
                 p.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                break;
            case "STORE":
                 p.addCommand(new StoreCommand(Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
                 break;
            case "ADD":
                p.addCommand(new ArithmeticCommand(ArithmeticEnum.ADD, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SUBTRACT":
                p.addCommand(new ArithmeticCommand(ArithmeticEnum.SUBTRACT, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "MULTIPLY":
                p.addCommand(new ArithmeticCommand(ArithmeticEnum.MULTIPLY, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "DIVIDE":
                p.addCommand(new ArithmeticCommand(ArithmeticEnum.DIVIDE, Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2]), Integer.parseInt(sArray[3])));
                break;
            case "SHOOT_FROM_MEM":
                p.addCommand(new ShootCommand(Integer.parseInt(sArray[1]), p));
                break;
            case "SENSE":
                p.addCommand(new SenseCommand(SenseEnum.NEAREST, Integer.parseInt(sArray[1])));
                break;
            default:
                
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
                if(line.equals("STORE")){
                    line = line + " " + firex.getValue() + " " + firey.getValue();
                }
                if(line.startsWith("LESS_THAN") || line.startsWith("GREATER") || line.startsWith("EQUAL") || line.startsWith("CLOSER")){
                    line = condArg1.getValue() + " " + line + " " + condArg2.getValue();
                }
                if(line.equals("SHOOT_FROM_MEM")){
                    line = line + " " + firex.getValue();
                }
                if(line.equals("SENSE")){
                    line = line + " " + firex.getValue();
                }
                if(line.equals("ADD") || line.equals("SUBTRACT") || line.equals("MULTIPLY") || line.equals("DIVIDE")){
                    line = line + " " + firex.getValue() + " " + firey.getValue() + " " + firez.getValue();
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