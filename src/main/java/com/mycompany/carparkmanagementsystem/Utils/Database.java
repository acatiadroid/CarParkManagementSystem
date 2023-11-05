package com.mycompany.carparkmanagementsystem.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database {

    String filePath = "VehicleDatabase.csv";
    List<List<String>> csvList = new ArrayList<>();

    public Database() {
        readCSV();
    }

    public String[][] allVehicles() {
        // Converts the ArrayList to a standard Array type.
        int csvLen = csvList.size();
        String[][] newArr;
        newArr = new String[csvLen][5];
        for (int x = 0; x < csvLen; x++) {
            for (int y = 0; y < 5; y++) {
                newArr[x][y] = csvList.get(x).get(y);
            }
        }
        return newArr;
    }

    public String[][] getVehiclesInCarPark() {
        // Filters out all vehicles that have exited the car park.
        String[][] listOfAllVehicles = allVehicles();
        String[][] arrayOfVehiclesInCarPark;
        int count = 0;

        for (String[] vehicle : listOfAllVehicles) {
            if ("notExited".equals(vehicle[3])) {
                count++;
            }
        }

        arrayOfVehiclesInCarPark = new String[count][5];

        int vehicleCount = 0;

        for (String[] vehicle : listOfAllVehicles) {
            if ("notExited".equals(vehicle[3])) {
                arrayOfVehiclesInCarPark[vehicleCount] = vehicle;
                vehicleCount++;
            }
        }
        return arrayOfVehiclesInCarPark;

    }

    public String[][] searchVRNRecords(String VRN) {
        // Searches for a vehicle registration number.
        String[][] listOfVehicles = allVehicles();
        String[][] records;

        int count = 0;
        for (int x = 0; x < listOfVehicles.length; x++) {
            if (listOfVehicles[x][0].equals(VRN)) {
                count++;
            }
        }

        records = new String[count][5];

        int recordCount = 0;
        for (String[] vehicle : listOfVehicles) {
            if (VRN.equals(vehicle[0])) {
                records[recordCount] = vehicle;
                recordCount++;
            }
        }

        return records;

    }

    public void amendRecord(int recordNumber, String VRN, String entryDate, String entryTime, String exitDate, String exitTime) {
        // Amends a specific record from the database.
        String[][] allVehicles = allVehicles();

        allVehicles[recordNumber][0] = VRN;
        allVehicles[recordNumber][1] = entryDate;
        allVehicles[recordNumber][2] = entryTime;
        allVehicles[recordNumber][3] = exitDate;
        allVehicles[recordNumber][4] = exitTime;

        writeToFile(allVehicles);
    }

    public String deleteRecord(int recordNumber) {
        // Deletes a specific record from the database.
        String[][] allVehicles = allVehicles();
        String[][] newArr = new String[allVehicles.length - 1][4];

        for (int i = 0, k = 0; i < allVehicles.length; i++) {
            if (i != recordNumber) {
                newArr[k] = allVehicles[i];
                k++;
            }
        }

        writeToFile(newArr);

        return allVehicles[recordNumber][0]; // returning deleted VRN to display to user in message box
    }

    public void addRecord(String VRN) {
        String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        String[][] allVehicles = allVehicles();
        String[][] newArr = new String[allVehicles.length + 1][5];

        int count = 0;
        for (String[] record : allVehicles) {
            newArr[count] = record;
            count++;
        }
        newArr[count][0] = VRN;
        newArr[count][1] = date;
        newArr[count][2] = time;
        newArr[count][3] = "notExited";
        newArr[count][4] = "notExited";

        writeToFile(newArr);
    }

    public int checkVehicleExited(String VRN) {
        // Checks whether the given VRN has exited the car park
        String[][] allVehicles = allVehicles();

        int lastOccurance = 0;
        int count = 0;
        for (String[] record : allVehicles) {
            if (record[0].equals(VRN)) {
                lastOccurance = count;
            }
            count++;
        }

        return lastOccurance;
    }

    public void exitVehicle(String VRN) {
        // "exits" a vehicle from the car park
        String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        int recordNum = checkVehicleExited(VRN);
        String[][] allVehicles = allVehicles();
        
        if (!"notExited".equals(allVehicles[recordNum][3])){
            System.out.println("Tried exiting vehicle that's already exited: " + VRN);
            return;
        }

        allVehicles[recordNum][3] = date;
        allVehicles[recordNum][4] = time;

        writeToFile(allVehicles);
    }

    // ------------------------------ PRIVATE METHODS -------------------------------
    private void readCSV() {
        // Reads the CSV file and writes it into an ArrayList object.
        // Credit: https://stackoverflow.com/questions/1474954/working-with-a-list-of-lists-in-java
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException ex) {
            System.out.println("Database file not found");
            return;
        }

        String line;

        try {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                List<String> csvPieces = new ArrayList<>(values.length);
                for (String piece : values) {
                    csvPieces.add(piece);
                }
                csvList.add(csvPieces);

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void writeToFile(String[][] updatedArray) {
        // Writes an array to a file in CSV format.
        String finalOutput = "";

        for (String[] record : updatedArray) {
            String recordToString = record[0] + "," + record[1] + "," + record[2] + "," + record[3] + "," + record[4] + "\n";
            finalOutput = finalOutput + recordToString;
        }

        try {
            FileWriter output = new FileWriter(filePath);
            output.write(finalOutput);
            output.close();
        } catch (IOException ex) {
            System.out.println("Failed to write to file: " + ex);
        }
    }
}
