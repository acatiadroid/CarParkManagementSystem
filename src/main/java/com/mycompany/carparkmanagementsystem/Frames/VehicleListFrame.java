package com.mycompany.carparkmanagementsystem.Frames;

import com.mycompany.carparkmanagementsystem.Utils.Database;
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
        String[] columnNames = {"VRN", "Entry Date", "Entry Time", "Exit Date", "Exit Time"};

        System.out.println(filteredList);

        JFrame vehicleListFrame = new JFrame("Vehicle List");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        try {
            vehicleListFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex) {
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        JButton amendButton = new JButton("Amend Selected Record");
        JButton deleteButton = new JButton("Delete selected record");

        mainPanel.add(sp, BorderLayout.CENTER);

        if (!filteredList) {
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

            mainFrame.setVisible(false);
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

                mainFrame.setVisible(false);
                JOptionPane.showMessageDialog(null, "Deleted record with VRN " + deletedVRN);

            }
        });

        vehicleListFrame.getContentPane().add(mainPanel);
        vehicleListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vehicleListFrame.setSize(500, 350);
        vehicleListFrame.setLocation(350, 50);
        vehicleListFrame.setVisible(true);

        mainFrame = vehicleListFrame;
    }
}
