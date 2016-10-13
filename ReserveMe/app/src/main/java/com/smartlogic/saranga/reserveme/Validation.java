package com.smartlogic.saranga.reserveme;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sranga on 5/27/2016.
 */
public class Validation {

    //phone number validation method
    public boolean isValidPhoneNumber(String phoneNumber){
        String PHONE_NUMBER_PATTERN ="^[0-9]{10}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    //email id validation method
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    //password validation method
    public boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 5) {
            return true;
        }
        return false;
    }
}
