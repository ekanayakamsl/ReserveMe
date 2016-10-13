package com.smartlogic.saranga.reserveme;

import junit.framework.TestCase;

/**
 * Created by sranga on 6/16/2016.
 */
public class ValidationTest extends TestCase {

    Validation validation;

    public void setUp() throws Exception {
        super.setUp();
        validation = new Validation();

    }

    public void testIsValidPhoneNumber() throws Exception {
        String phoneNumbers[] = {"0714269395","sadsad","0124332","32423523525"};
        boolean exp[] = {true,false,false,false};

        for (int i= 0;i<4;i++){
            boolean result = validation.isValidPhoneNumber(phoneNumbers[i]);
            assertEquals(exp[i],result);
        }
    }

    public void testIsValidEmail() throws Exception {

        String phoneNumbers[] = {"ekanayaka.msl@gamil.com","ssaf,saf","esfsfgmai.com","@gamil.com","12424"};
        boolean exp[] = {true,false,false,false,false};

        for (int i= 0;i<5;i++){
            boolean result = validation.isValidEmail(phoneNumbers[i]);
            assertEquals(exp[i],result);
        }

    }

    public void testIsValidPassword() throws Exception {
        String phoneNumbers[] = {"aaaaa","aaaa","aaaaaa","aaaaaaadadadadaddaddadadad","12424"};
        boolean exp[] = {false,false,true,true,false};

        for (int i= 0;i<2;i++){
            boolean result = validation.isValidPassword(phoneNumbers[i]);
            assertEquals(exp[i],result);
        }
    }
}