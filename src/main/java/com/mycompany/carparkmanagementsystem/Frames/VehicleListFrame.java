package com.mycompany.carparkmanagementsystem.Frames;

// Local imports
import com.mycompany.carparkmanagementsystem.Utils.Database;

// External imports
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class VehicleListFrame {

    private JFrame mainFrame;

    public VehicleListFrame(String[][] data, boolean filteredList) {
        String[] columnNames = {"VRN", "Entry Date", "Entry Time", "Exit Date", "Exit Time"}; // Table titles

        JFrame vehicleListFrame = new JFrame("Vehicle List");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // Creates a 1 x 2 table.

        try { // tries adding the icon to the window, and skips if file not found.
            vehicleListFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex) {
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table); // Adds a scroll bar to the window to scroll through records
        JButton amendButton = new JButton("Amend Selected Record");
        JButton deleteButton = new JButton("Delete selected record");

        mainPanel.add(sp, BorderLayout.CENTER);

        if (!filteredList) { // Add buttons to the bottom only if the list isn't filtered (I.E. only showing vehicles in car park).
            buttonPanel.add(amendButton);
            buttonPanel.add(deleteButton);
            mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
        }

        amendButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                amendButton.setText("Nothing selected");
                return;
            }

            amendButton.setText("Amend selected Record");

            AmendRecordFrame amendRecordFrame = new AmendRecordFrame();
            amendRecordFrame.populateTextFields(row);
            System.out.println("Opened AmendRecordWindow");

            mainFrame.dispose();
        }
        );

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                deleteButton.setText("Nothing selected");
                return;
            }

            deleteButton.setText("Delete selected Record");

            int dialogResult = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm deletion", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Database db = new Database();
                String deletedVRN = db.deleteRecord(row);

                mainFrame.dispose();
                JOptionPane.showMessageDialog(null, "Deleted record with VRN " + deletedVRN);

            }
        });

        vehicleListFrame.getContentPane().add(mainPanel);
        vehicleListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vehicleListFrame.setSize(500, 350);
        vehicleListFrame.setLocation(350, 50); // Opens window to the right of the screen
        vehicleListFrame.setVisible(true);

        mainFrame = vehicleListFrame;
    }
}
