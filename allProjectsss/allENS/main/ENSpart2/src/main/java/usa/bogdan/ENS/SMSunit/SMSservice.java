package usa.bogdan.ENS.SMSunit;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import usa.bogdan.ENS.Entity.CSVuser;

import java.util.List;

//@Service
//public class SMSservice {
//    @Value("${twilio.account.sid}")
//    private String twilioSID;
//    @Value("${twilio.auth.token}")
//    private String twilioTOKEN;
//    @Value("${twilio.phone.number}")
//    private String twilioNUMBER;
//    @PostConstruct
//    private void init() {
//        Twilio.init(twilioSID, twilioTOKEN);
//    }
//    public String sendSMS(List<CSVuser> userSMS, String message) {
//        for (CSVuser go: userSMS) {
//            Message.creator(
//                    new PhoneNumber(go.getContactValue()),
//                    new PhoneNumber(twilioNUMBER),
//                    message
//            ).create();
//        }
//        return "sent";
//    }
//}