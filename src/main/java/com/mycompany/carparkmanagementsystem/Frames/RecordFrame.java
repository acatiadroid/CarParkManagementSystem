package com.mycompany.carparkmanagementsystem.Frames;

import com.mycompany.carparkmanagementsystem.Database.Database;
import com.mycompany.carparkmanagementsystem.Validation.Validation;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RecordFrame {
    JFrame mainFrame;
    
    public RecordFrame() {
        JFrame recordFrame = new JFrame("Record Mode");
        try{
            recordFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex){}
        
        JPanel contentPanel = new JPanel(new GridLayout(3, 0));

        JLabel title = new JLabel("Enter the Vehicle Registration:");
        title.setHorizontalAlignment(0);
        title.setVerticalAlignment(0);

        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        JTextField inputVRN = new JTextField(1);
        inputVRN.setHorizontalAlignment(JTextField.CENTER);
        inputVRN.setFont(font1);

        JButton enterButton = new JButton("Enter");
        contentPanel.add(title);
        contentPanel.add(inputVRN);
        contentPanel.add(enterButton);

        recordFrame.setContentPane(contentPanel);
        recordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recordFrame.setSize(300, 200);
        recordFrame.setVisible(true);
        
        mainFrame = recordFrame;
        
        enterButton.addActionListener(e -> {
            String VRN = inputVRN.getText();
            Validation validate = new Validation();
            boolean validVRN = validate.checkVRNFormat(VRN);
            
            if (validVRN == false) {
                JOptionPane.showMessageDialog(null, "Vehicle Registration Number is incorrect.\nExample format: YK19ABC");
                return;
            }
  
            Database db = new Database();
            
            if(db.checkVehicleExited(VRN) != 0){
                db.exitVehicle(VRN);
                JOptionPane.showMessageDialog(null, VRN + " has exited the car park.");
                inputVRN.setText("");
            } else{
                db.addRecord(VRN.toUpperCase());
                JOptionPane.showMessageDialog(null, "Entrance for " + VRN + " has been recorded.");
                inputVRN.setText("");
            }
        });

    }
}
