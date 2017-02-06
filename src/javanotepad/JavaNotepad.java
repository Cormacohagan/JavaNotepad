package javanotepad;

/*
 * @author CormacOHagan
 * Simple Java Notepad with save, open and close functionality.
 * TODO: Add font options, character & line count,
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class JavaNotepad extends JFrame implements ActionListener {

    private TextArea textBox = new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY); //Text area
    private MenuBar menuBar = new MenuBar(); //Menu Bar
    private Menu file = new Menu(); //File menu
    private Menu help = new Menu(); //Help menu
    private MenuItem save = new MenuItem(); //Submenu Item Save
    private MenuItem open = new MenuItem(); //Submenu Item Open
    private MenuItem close = new MenuItem(); //Submenu Item Close
    private MenuItem about = new MenuItem(); //Submenu Item About
    private int size = 14;


    public JavaNotepad(){

        //Font & Default Settings
        this.textBox.setFont(new Font("Consolas",Font.PLAIN,size)); //Set font style (Font, style, size)
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Create close operation
        this.setSize(1000,800); //Set default window size
        this.setTitle("Cormac's Notepad"); //Set window title
        this.getContentPane().setLayout(new BorderLayout()); //Set layout to border layout
        this.getContentPane().add(textBox); //Add the previously created Text Area
        
        //Add Menu Bar
        this.setMenuBar(this.menuBar); 
        
        //Add main menu options  
        this.menuBar.add(this.file); //Add file option to main menu bar
        this.file.setLabel("File"); //Label button "File"
        
        this.menuBar.add(this.help);
        this.help.setLabel("Help");
        
        //File submenus
            this.save.setLabel("Save"); //Label button "Save"
            this.save.addActionListener(this); //Add action lister to button
            this.save.setShortcut(new MenuShortcut(KeyEvent.VK_S, false)); //Add keyboard shortcut to button
            this.file.add(this.save); //Add button to "file" submenu

            //Repeat for other submenu buttons
            this.open.setLabel("Open");
            this.open.addActionListener(this);
            this.open.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
            this.file.add(this.open);

            this.close.setLabel("Close");
            this.close.addActionListener(this);
            this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
            this.file.add(this.close);  
            
            this.about.setLabel("About");
            this.about.addActionListener(this);
            this.help.add(this.about);
    }
    
    @Override
    public void actionPerformed (ActionEvent e){
       
        //Adding functionality to menu items
        //Save Button
        if (e.getSource() == this.save){ //Listening for user interacting with save button
            JFileChooser saveFile = new JFileChooser(); //Create a JFileChooser
            int option = saveFile.showSaveDialog(this); //Choose the "Save" JFileChooser dialog
            
            if (option == JFileChooser.APPROVE_OPTION){ //When the user confirms their file save
                try{
                    BufferedWriter out = new BufferedWriter(new FileWriter(saveFile.getSelectedFile().getPath())); //Create buffered writer to output text to file.
                    out.write(this.textBox.getText()); //Write text from document to output file
                    out.close(); //end output stream.
                } catch (Exception exc){ //if there is an error throw exception
                    System.out.println(exc.getMessage()); //output exception
                }
            }      
        }
        
        //Open Button
        else if (e.getSource() == this.open){ //Listening for user interacting with open button
            JFileChooser openFile = new JFileChooser(); //Create a JFileChooser
            int option = openFile.showOpenDialog(this); //Choose the "Open" JFileChooser dialog
            
            if (option == JFileChooser.APPROVE_OPTION){ //When the user confirms their file open
                this.textBox.setText(""); //Clear text area on current document
                try {
                    Scanner scanner = new Scanner(new FileReader(openFile.getSelectedFile().getPath())); //Create scanner to read text from chosen document
                    while (scanner.hasNext()) //Create loop to read each line of text
                        this.textBox.append(scanner.nextLine() + "\n"); //Update scanner and move to next line of text
                } catch (Exception exc){ //if there is an error throw exception
                    System.out.println(exc.getMessage()); //output exception
                }
            }           
        }
        
       //Close Button
        else if (e.getSource() == this.close) //Listening for user interacting with close button
            this.dispose(); //Using dispose instead of System.exit because I only want to close the current window rather than the entire VM.     
        
        else if (e.getSource() == this.about){
            JOptionPane.showMessageDialog(this, "Java Notepad v0.01 by Cormac O'Hagan");
        }
    } 
    
    public static void main(String[] args) {
      //Run the notepad program
        JavaNotepad runApp = new JavaNotepad();
        runApp.setVisible(true); //Ensure that the window is visible.      
    }
}
