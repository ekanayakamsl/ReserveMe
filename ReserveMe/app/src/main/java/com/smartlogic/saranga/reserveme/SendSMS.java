package com.smartlogic.saranga.reserveme;

import android.telephony.SmsManager;

import java.util.Random;

/**
 * Created by sranga on 5/30/2016.
 */
public class SendSMS  {
    private String phoneNumber;

    public SendSMS(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void sendVerificationCode(){


        Random random = new Random();
        String verificationCode = random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9)+""+random.nextInt(9)+"";
        String message = "Please vefify your ReserveMe account... Your verification code is : "+verificationCode;

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);


    }
}
