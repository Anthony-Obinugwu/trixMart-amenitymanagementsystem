package com.education.amenity.management;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {

    private final String accountSid;
    private final String authToken;
    private final String fromPhoneNumber;

    // Constructor to inject values
    public WhatsappService(
            @Value("${twilio.account.sid}") String accountSid,
            @Value("${twilio.auth.token}") String authToken,
            @Value("${twilio.phone.number}") String fromPhoneNumber
    ) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromPhoneNumber = fromPhoneNumber;

        // Initialize Twilio with injected credentials
        Twilio.init(accountSid, authToken);
    }

    public void sendMessage(String toPhoneNumber, String messageBody) {
        Message.creator(
                new PhoneNumber("whatsapp:" + toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();
    }
}
