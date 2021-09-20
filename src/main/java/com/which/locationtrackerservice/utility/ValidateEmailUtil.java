package com.which.locationtrackerservice.utility;

import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Component
public class ValidateEmailUtil {

    public boolean isEmailAddressValid(String emailId){
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(emailId);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
