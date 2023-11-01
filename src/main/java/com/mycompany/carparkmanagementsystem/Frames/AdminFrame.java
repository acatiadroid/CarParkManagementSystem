package com.mycompany.carparkmanagementsystem.Frames;

import com.mycompany.carparkmanagementsystem.Database.Database;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class AdminFrame {
    private JFrame mainFrame;
    
    public AdminFrame(){
        JFrame adminFrame = new JFrame("Admin Mode");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(3, 0));
        
        try{
            adminFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex){}
        
        JButton displayVehicles = new JButton("Display vehicles in car park");
        JButton amendRecord = new JButton("Amend/Delete a record");
        JButton searchVRN = new JButton("Search for a VRN");
        buttonPanel.add(displayVehicles);
        buttonPanel.add(amendRecord);
        buttonPanel.add(searchVRN);

        JButton switchModes = new JButton("Switch Modes");

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(switchModes, BorderLayout.PAGE_END);
        
        switchModes.addActionListener(e -> {
            mainFrame.setVisible(false);
            new ModeSelectionFrame();
            System.out.println("Opened ModeSelectionFrame");
            }
        );
        
        displayVehicles.addActionListener(e -> {
            Database db = new Database();
            String[][] vehicleList = db.getVehiclesInCarPark();
            
            new VehicleListFrame(vehicleList);
            System.out.println("Opened VehicleListFrame : VehiclesInCarPark");
            }
        );
        
        amendRecord.addActionListener(e -> {
            Database db = new Database();
            String[][] vehicleList = db.allVehicles();
            new VehicleListFrame(vehicleList);
            System.out.println("Opened VehicleListFrame : AllVehicles");
            }
        );
        
        searchVRN.addActionListener(e -> {
                String result = (String)JOptionPane.showInputDialog(
               mainFrame,
               "Enter a vehicle registration number", 
               "Search for a VRN",            
               JOptionPane.PLAIN_MESSAGE,
               null,            
               null, 
               ""
            );
            if(result != null && result.length() > 0){
                Database db = new Database();
                String[][] recordList = db.searchVRNRecords(result);
                                
                new VehicleListFrame(recordList);
                System.out.println("Opened VehicleListFrame : Search");
            }}
        );
  
        adminFrame.setContentPane(mainPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(300, 325);
        adminFrame.setVisible(true);
        
        mainFrame = adminFrame;
    }
}
