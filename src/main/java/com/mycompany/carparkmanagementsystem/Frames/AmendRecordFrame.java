package com.mycompany.carparkmanagementsystem.Frames;

// Local imports
import com.mycompany.carparkmanagementsystem.Utils.Database;
import com.mycompany.carparkmanagementsystem.Utils.Validation;

// External imports
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

    private int recNum; // the record number of the record we're amending

    private final JTextField VRNInput = new JTextField();        // VRN inputted by user
    private final JTextField entryDateInput = new JTextField();  // Entry Date inputted by user
    private final JTextField entryTimeInput = new JTextField();  // Entry Time inputted by user
    private final JTextField exitDateInput = new JTextField();   // Exit Date inputted by user
    private final JTextField exitTimeInput = new JTextField();   // Exit Time inputted by user

    public AmendRecordFrame() {
        JFrame amendRecordFrame = new JFrame("Amend a Record");
        JPanel contentPanel = new JPanel(new GridLayout(7, 2)); // Creates a 7 x 2 grid which is the necessary size for all components.

        try { // tries adding the icon to the window, and skips if file not found.
            amendRecordFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex) {
        }
        JLabel title = new JLabel("Amend a Record");
        title.setHorizontalAlignment(0);  // Center the title label
        title.setVerticalAlignment(0);

        JLabel VRNText = new JLabel("VRN:");
        JCheckBox vehicleExited = new JCheckBox("Vehicle has exited");
        JLabel entryDateText = new JLabel("Entry Date:");
        JLabel entryTimeText = new JLabel("Entry Time:");
        JLabel exitDateText = new JLabel("Exit Date:");
        JLabel exitTimeText = new JLabel("Exit Time:");

        JButton submitButton = new JButton("Amend");
        JButton backButton = new JButton("Back");

        // Insert the components into the 7 x 2 grid
        contentPanel.add(vehicleExited);
        contentPanel.add(new JLabel("")); // empty label so grid layout is layed out properly
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
        contentPanel.add(backButton);

        mainWindow = amendRecordFrame;

        vehicleExited.addActionListener(e -> {
            // Makes the text boxes uneditable when "Vehicle has exited" checkbox is ticked
            if (vehicleExited.isSelected()) {
                VRNInput.setEditable(false);
                entryDateInput.setEditable(false);
                entryTimeInput.setEditable(false);
                exitDateInput.setEditable(false);
                exitTimeInput.setEditable(false);
            } else { // if unticked, make boxes editable again
                VRNInput.setEditable(true);
                entryDateInput.setEditable(true);
                entryTimeInput.setEditable(true);
                exitDateInput.setEditable(true);
                exitTimeInput.setEditable(true);
            }
        });

        submitButton.addActionListener(e -> {
            Database db = new Database();

            // If the vehicle has already exited and the user wants to 'exit' it
            // again, ask them for confirmation and update the exit date & time
            // to current time.
            if (vehicleExited.isSelected()) {
                if (!exitDateInput.getText().equals("notExited")) {
                    int dialogResult = JOptionPane.showConfirmDialog(
                            null,
                            "This vehicle has been recorded as exited. Do you want to overwrite it?",
                            "Record already exited",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (dialogResult == JOptionPane.YES_OPTION) { // if 'Yes' is selected on confirmation.
                        db.amendRecord(
                                recNum,
                                VRNInput.getText(),
                                entryDateInput.getText(),
                                entryTimeInput.getText(),
                                exitDateInput.getText(),
                                exitTimeInput.getText()
                        );
                    } else {
                        return;
                    }
                }

                db.exitVehicle(VRNInput.getText()); // amend database to exit vehicle with current date & time
                mainWindow.dispose(); // destroy window. Wipes it from memory.
                JOptionPane.showMessageDialog(null, "Vehicle has been recorded as exited.");

                return;
            }
            Validation validate = new Validation();

            String VRN = validate.convertToUppercase(VRNInput.getText());
            String entryDate = entryDateInput.getText();
            String entryTime = entryTimeInput.getText();
            String exitDate = exitDateInput.getText();
            String exitTime = exitTimeInput.getText();

            // Validation to determine whether the data entered is sensible data.
            // This only checks whether it's sensible, not correct. It checks whether
            // the date format follows 00/00/0000, time format follows 00:00:00, and
            // VRN format follows XX00XXX
            boolean validVRN = validate.checkVRNFormat(VRN);
            boolean validEntryDate = validate.checkDateFormat(entryDate);
            boolean validExitDate = validate.checkDateFormat(exitDate);
            boolean validEntryTime = validate.checkTimeFormat(entryTime);
            boolean validExitTime = validate.checkTimeFormat(exitTime);

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

            db.amendRecord( // Validation has passed, amend database
                    recNum,
                    VRN,
                    entryDate,
                    entryTime,
                    exitDate,
                    exitTime
            );
            JOptionPane.showMessageDialog(null, "Record has been amended.");

            mainWindow.dispose();
        });

        backButton.addActionListener(e -> { // Closes the window and opens the admin frame.
            mainWindow.dispose();
        });

        amendRecordFrame.setContentPane(contentPanel);
        amendRecordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Disposes the current window on close and doesn't exit the entire program.
        amendRecordFrame.setSize(300, 250);
        amendRecordFrame.setLocation(350, 50); // Offsets the window to the right of the Admin Frame.
        amendRecordFrame.setVisible(true);
    }

    public void populateTextFields(int recordNumber) {
        // When loading the window, this is called to populate the text field
        // with the pre-existing data in the selected record.
        Database db = new Database();
        String[][] allRecords = db.allVehicles();
        String[] record = allRecords[recordNumber];

        VRNInput.setText(record[0]);
        entryDateInput.setText(record[1]);
        entryTimeInput.setText(record[2]);
        exitDateInput.setText(record[3]);
        exitTimeInput.setText(record[4]);

        recNum = recordNumber; // needed later for referencing in database.
    }
}
