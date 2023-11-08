package com.mycompany.carparkmanagementsystem.Utils;

// External imports
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public boolean checkVRNFormat(String input) {
        // Checks the format matches XX00XXX
        String regex = ".{2}\\d{2}.{3}"; // Regex pattern sourced from ChatGPT.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches(); // returns true or false depending on whether they've matched.
    }

    public boolean checkDateFormat(String input) {
        // Checks the format matches 00/00/0000
        String regex = "\\d{2}/\\d{2}/\\d{4}"; // Regex pattern sourced from ChatGPT.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches(); // returns true or false depending on whether they've matched.
    }

    public boolean checkTimeFormat(String input) {
        // Checks the format matches 00:00:00
        String regex = "\\d{2}:\\d{2}:\\d{2}"; // Regex pattern sourced from ChatGPT.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches(); // returns true or false depending on whether they've matched.
    }
}
