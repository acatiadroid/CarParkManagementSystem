package com.mycompany.carparkmanagementsystem.Frames;

// External Imports
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class ModeSelectionFrame {

    private JFrame mainFrame;

    public ModeSelectionFrame() {
        JFrame modeSelectionFrame = new JFrame("Select Mode");
        JPanel contentPanel = new JPanel(new GridLayout(3, 1)); // Creates a 3 x 1 grid

        try { // tries adding the icon to the window, and skips if file not found.
            modeSelectionFrame.setIconImage(ImageIO.read(new File("img/icon.png")));
        } catch (IOException ex) {
        }
        JLabel title = new JLabel("Select a mode:");
        title.setHorizontalAlignment(0); // Centers the label
        title.setVerticalAlignment(0);

        JButton recordModeButton = new JButton("Record Mode");
        JButton adminModeButton = new JButton("Admin Mode");
        contentPanel.add(title);
        contentPanel.add(recordModeButton);
        contentPanel.add(adminModeButton);

        recordModeButton.addActionListener(e -> {
            new RecordFrame(); // Opens record mode window
            mainFrame.dispose(); // Closes mode selection window
            System.out.println("Opened RecordFrame");
        }
        );
        adminModeButton.addActionListener(e -> {
            new AdminFrame();
            mainFrame.dispose();
            System.out.println("Opened AdminFrame");
        }
        );

        modeSelectionFrame.setContentPane(contentPanel);
        modeSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits the entire program when window is closed
        modeSelectionFrame.setSize(300, 200);
        modeSelectionFrame.setLocation(50, 50);
        modeSelectionFrame.setVisible(true);

        mainFrame = modeSelectionFrame;
    }
}
