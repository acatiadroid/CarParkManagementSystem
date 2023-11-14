package com.mycompany.carparkmanagementsystem.Utils;

// External imports
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public boolean checkVRNFormat(String input) {
        // Checks the format matches XX00XXX, in uppercase only
        String regex = "[A-Z]{2}\\d{2}[A-Z]{3}"; // Regex pattern sourced from ChatGPT.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches(); // returns true or false depending on whether they've matched.
    }

    public boolean checkDateFormat(String input) {
        // Checks the format matches 00/00/0000
        if (input.equals("notExited")) { // if vehicle hasn't exited, don't do validation on these fields.
            return true;
        }

        String regex = "\\d{2}/\\d{2}/\\d{4}"; // Regex pattern sourced from ChatGPT.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches(); // returns true or false depending on whether they've matched.
    }

    public boolean checkTimeFormat(String input) {
        // Checks the format matches 00:00:00
        if (input.equals("notExited")) { // if vehicle hasn't exited, don't do validation on these fields.
            return true;
        }

        String regex = "\\d{2}:\\d{2}:\\d{2}"; // Regex pattern sourced from ChatGPT.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches(); // returns true or false depending on whether they've matched.
    }

    public String convertToUppercase(String input) {
        // Converts a string to uppercase
        return input.toUpperCase();
    }
}
