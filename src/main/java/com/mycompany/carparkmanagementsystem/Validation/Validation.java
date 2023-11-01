package com.mycompany.carparkmanagementsystem.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public boolean checkVRNFormat(String input){
        // Checks the format matches XX00XXX
        String regex = ".{2}\\d{2}.{3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    public boolean checkDateFormat(String input){
        // Checks the format matches 00/00/0000
        String regex = "\\d{2}/\\d{2}/\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    public boolean checkTimeFormat(String input){
        // Checks the format matches 00:00:00
        String regex = "\\d{2}:\\d{2}:\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
}
