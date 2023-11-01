package com.mycompany.carparkmanagementsystem.Frames;

import com.mycompany.carparkmanagementsystem.Database.Database;
import com.mycompany.carparkmanagementsystem.Validation.Validation;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class AmendRecordFrame {
    JFrame mainWindow;
    
    private int recNum;
    
    private final JTextField VRNInput = new JTextField();
    private final JTextField entryDateInput = new JTextField();
    private final JTextField entryTimeInput = new JTextField();
    private final JTextField exitDateInput = new JTextField();
    private final JTextField exitTimeInput = new JTextField();

    public AmendRecordFrame() {
        JFrame amendRecordFrame = new JFrame("Amend a Record");
        JPanel contentPanel = new JPanel(new GridLayout(6, 2));
        
        try{
            amendRecordFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex){}
        JLabel title = new JLabel("Amend a Record");
        title.setHorizontalAlignment(0);
        title.setVerticalAlignment(0);

        JLabel VRNText = new JLabel("VRN:");
        JLabel entryDateText = new JLabel("Entry Date:");
        JLabel entryTimeText = new JLabel("Entry Time:");
        JLabel exitDateText = new JLabel("Exit Date:");
        JLabel exitTimeText = new JLabel("Exit Time:");

        JButton submitButton = new JButton("Amend");

        contentPanel.add(VRNText);
        contentPanel.add(VRNInput);
        contentPanel.add(entryDateText);
        contentPanel.add(entryDateInput);
        contentPanel.add(entryTimeText);
        contentPanel.add(entryTimeInput);
        contentPanel.add(exitDateText);
        contentPanel.add(exitDateInput);
        contentPanel.add(exitTimeText);
        contentPanel.add(exitTimeInput);
        contentPanel.add(submitButton);

        amendRecordFrame.setContentPane(contentPanel);
        amendRecordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        amendRecordFrame.setSize(250, 300);
        amendRecordFrame.setVisible(true);
        
        mainWindow = amendRecordFrame;
        
        submitButton.addActionListener(e -> {
            Database db = new Database();
            Validation validate = new Validation();
            boolean validVRN = validate.checkVRNFormat(VRNInput.getText());
            boolean validEntryDate = validate.checkDateFormat(entryDateInput.getText());
            boolean validExitDate = validate.checkDateFormat(exitDateInput.getText());
            boolean validEntryTime = validate.checkTimeFormat(entryTimeInput.getText());
            boolean validExitTime = validate.checkTimeFormat(exitTimeInput.getText());
            
            if (!validVRN) {
                JOptionPane.showMessageDialog(null, "Vehicle Registration Number is incorrect.\nExample format: YK19ABC");
                return;
            }
            if (!validEntryDate) {
                JOptionPane.showMessageDialog(null, "Entry date format is invalid\nExample format: 01/02/2023");
                return;
            }
            if (!validExitDate) {
                JOptionPane.showMessageDialog(null, "Exit date format is invalid\nExample format: 01/02/2023");
                return;
            }
            if (!validEntryTime) {
                JOptionPane.showMessageDialog(null, "Entry time is invalid\nExample format: 09:24:56");
                return;
            }
            if (!validExitTime) {
                JOptionPane.showMessageDialog(null, "Exit time is invalid\nExample format: 09:24:56");
                return;
            }
            
            db.amendRecord(
                recNum,
                VRNInput.getText(),
                entryDateInput.getText(),
                entryTimeInput.getText(),
                exitDateInput.getText(),
                exitTimeInput.getText()
            );
            
            mainWindow.setVisible(false);
            JOptionPane.showMessageDialog(null, "Record has been amended");
            }   
        );
    }

    public void populateTextFields(int recordNumber) {
        Database db = new Database();
        String[][] allRecords = db.allVehicles();
        String[] record = allRecords[recordNumber];

        VRNInput.setText(record[0]);
        entryDateInput.setText(record[1]);
        entryTimeInput.setText(record[2]);
        exitDateInput.setText(record[3]);
        exitTimeInput.setText(record[4]);
        
        recNum = recordNumber;
    }
}
