package com.mycompany.carparkmanagementsystem.Frames;

// Local imports
import com.mycompany.carparkmanagementsystem.Utils.Database;
import com.mycompany.carparkmanagementsystem.Utils.Validation;

// External imports
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

    public AdminFrame() {
        JFrame adminFrame = new JFrame("Admin Mode");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1)); // Creates a 3 x 1 grid

        try { // tries adding the icon to the window, and skips if file not found.
            adminFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex) {
        }

        JButton displayVehicles = new JButton("Display vehicles in car park");
        JButton amendRecord = new JButton("Amend/Delete a record");
        JButton searchVRN = new JButton("Search for a VRN");
        buttonPanel.add(displayVehicles);
        buttonPanel.add(amendRecord);
        buttonPanel.add(searchVRN);

        JButton switchModes = new JButton("Switch Modes");

        mainPanel.add(buttonPanel, BorderLayout.CENTER);   // Adds button panel to center of menu
        mainPanel.add(switchModes, BorderLayout.PAGE_END); // Adds switch mode button to footer of menu

        switchModes.addActionListener(e -> {
            mainFrame.dispose();
            new ModeSelectionFrame();
            System.out.println("Opened ModeSelectionFrame");
        }
        );

        displayVehicles.addActionListener(e -> {
            Database db = new Database();
            String[][] vehicleList = db.getVehiclesInCarPark(); // Retrieves all vehicles in the car park from the database

            new VehicleListFrame(vehicleList, true); // Creates new Vehicle List frame with list of vehicles in car park
            System.out.println("Opened VehicleListFrame : VehiclesInCarPark");
        }
        );

        amendRecord.addActionListener(e -> {
            Database db = new Database();
            String[][] vehicleList = db.allVehicles();
            new VehicleListFrame(vehicleList, false);
            System.out.println("Opened VehicleListFrame : AllVehicles");
        });

        searchVRN.addActionListener(e -> {
            String result = (String) JOptionPane.showInputDialog(
                    null,
                    "Enter a vehicle registration number",
                    "Search for a VRN",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""
            );
            if (result != null && result.length() > 0) {
                Database db = new Database();

                Validation validate = new Validation();
                boolean validVRN = validate.checkVRNFormat(result);

                if (!validVRN) { // VRN format is incorrect
                    JOptionPane.showMessageDialog(null, "Vehicle Registration Number is the incorrect format.");
                    return;
                }
                String[][] recordList = db.searchVRNRecords(validate.convertToUppercase(result)); // search database for user input.

                new VehicleListFrame(recordList, true);
                System.out.println("Opened VehicleListFrame : Search");
            }
        }
        );

        adminFrame.setContentPane(mainPanel);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits the entire program
        adminFrame.setSize(300, 325);
        adminFrame.setLocation(50, 50);
        adminFrame.setVisible(true); // Makes window visible on screen

        mainFrame = adminFrame;
    }
}
