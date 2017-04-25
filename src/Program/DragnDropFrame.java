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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
    DefaultListModel model;
    SpinnerModel spinnerModel;
    String[] Cmds = {"MOVE NORTH", "MOVE SOUTH", "MOVE EAST", "MOVE WEST", "SHOOT", "IF,COND(,),{,}ENDIF", "WHILE,COND(,),{,}ENDWHILE"};
    JButton done;
    JButton save;
    JButton clear;
    JSpinner firex;
    JSpinner firey;
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
     //not using a menu bar anymore
     /*setJMenuBar(menuBar);
     JMenu mnFile = new JMenu("file");
    menuBar.add(mnFile);
    JMenuItem mntmChooseFileLocation = new JMenuItem("Choose File Location");
		mntmChooseFileLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open JFileChooser
				int fcValue = fc.showOpenDialog(null);
				if(fcValue == fc.APPROVE_OPTION){
					f = fc.getSelectedFile();
					done.setEnabled(true);
				}
			}
		});
		mnFile.add(mntmChooseFileLocation);
                */
                
                
       
        firex = new JSpinner();
        firey = new JSpinner();
        firePanel.add(new JLabel("Shoot x value: "));
        firePanel.add(firex);
        firePanel.add(new JLabel("Shoot y value: "));
        firePanel.add(firey);

        
        
        JScrollPane pane = new JScrollPane();
        pane.setPreferredSize(new Dimension(300, 300));

        commandList = new JList(Cmds);
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

//        panel.add(field);
        panel.add(commandList);
        panel.add(firePanel);
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
        for (int i = 0; i < model.size(); i++){
            String s = (String) model.get(i);
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
