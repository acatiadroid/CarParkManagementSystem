package com.mycompany.carparkmanagementsystem;

import com.mycompany.carparkmanagementsystem.Frames.ModeSelectionFrame;
import java.io.File;
import java.io.IOException;

public class CarParkManagementSystem {

    public static void main(String[] args) {
        String filePath = "VehicleDatabase.csv";
        File file = new File(filePath);

        if (!file.exists()) { // checking if the CSV file exists
            try {
                file.createNewFile(); // create an empty file
                System.out.println("Created " + filePath + " because it doesn't exist.");
            } catch (IOException ignored) {
            }
        } else {
            System.out.println("Verified " + filePath + " exists.");
        }
        
        new ModeSelectionFrame(); // loads the UI instance
        System.out.println("Opened ModeSelectionFrame");
    }
}
