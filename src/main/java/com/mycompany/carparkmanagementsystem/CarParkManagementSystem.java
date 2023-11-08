package com.mycompany.carparkmanagementsystem;

// Local imports
import com.mycompany.carparkmanagementsystem.Frames.ModeSelectionFrame;

// External imports
import java.io.File;
import java.io.IOException;

public class CarParkManagementSystem {

    public static void main(String[] args) {
        String filePath = "VehicleDatabase.csv"; // File path to the database file
        File file = new File(filePath);

        if (!file.exists()) { // checking if the CSV file exists
            try {
                file.createNewFile(); // create an empty file
                System.out.println("Created " + filePath + " because it doesn't exist.");
            } catch (IOException ignored) { // catch potential IO errors with the file (I.E. out of storage)
            }
        } else {
            System.out.println("Verified " + filePath + " exists.");
        }
        
        new ModeSelectionFrame(); // loads the UI instance
        System.out.println("Opened ModeSelectionFrame");
    }
}
