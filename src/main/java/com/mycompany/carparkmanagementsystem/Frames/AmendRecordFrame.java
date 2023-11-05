package com.mycompany.carparkmanagementsystem.Frames;

import com.mycompany.carparkmanagementsystem.Utils.Database;
import com.mycompany.carparkmanagementsystem.Utils.Validation;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
        JPanel contentPanel = new JPanel(new GridLayout(7, 2));

        try {
            amendRecordFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex) {
        }
        JLabel title = new JLabel("Amend a Record");
        title.setHorizontalAlignment(0);
        title.setVerticalAlignment(0);

        JLabel VRNText = new JLabel("VRN:");
        JCheckBox vehicleExited = new JCheckBox("Vehicle has exited");
        JLabel entryDateText = new JLabel("Entry Date:");
        JLabel entryTimeText = new JLabel("Entry Time:");
        JLabel exitDateText = new JLabel("Exit Date:");
        JLabel exitTimeText = new JLabel("Exit Time:");

        JButton submitButton = new JButton("Amend");

        contentPanel.add(vehicleExited);
        contentPanel.add(new JLabel("")); // empty label so
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

        mainWindow = amendRecordFrame;

        vehicleExited.addActionListener(e -> {
            if (vehicleExited.isEnabled()) {
                VRNInput.setEditable(false);
                entryDateInput.setEditable(false);
                entryTimeInput.setEditable(false);
                exitDateInput.setEditable(false);
                exitTimeInput.setEditable(false);
            } else {
                VRNInput.setEditable(true);
                entryDateInput.setEditable(true);
                entryTimeInput.setEditable(true);
                exitDateInput.setEditable(true);
                exitTimeInput.setEditable(true);
            }
        }
        );

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

            if (vehicleExited.isEnabled()) {
                db.exitVehicle(VRNInput.getText());
                JOptionPane.showMessageDialog(null, "Vehicle has been recorded as exited.");
            } else {
                db.amendRecord(
                        recNum,
                        VRNInput.getText(),
                        entryDateInput.getText(),
                        entryTimeInput.getText(),
                        exitDateInput.getText(),
                        exitTimeInput.getText()
                );
                JOptionPane.showMessageDialog(null, "Record has been amended.");
            }

            mainWindow.setVisible(false);

        }
        );

        amendRecordFrame.setContentPane(contentPanel);
        amendRecordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        amendRecordFrame.setSize(300, 250);
        amendRecordFrame.setLocation(50, 50);
        amendRecordFrame.setVisible(true);
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
