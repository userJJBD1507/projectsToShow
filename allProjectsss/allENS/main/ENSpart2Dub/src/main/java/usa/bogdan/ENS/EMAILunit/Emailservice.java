package usa.bogdan.ENS.EMAILunit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import usa.bogdan.ENS.Entity.CSVuser;

import java.util.List;

@Service
public class Emailservice {
    @Autowired
    private JavaMailSender javaMailSender;
    public String sendEmail(List<CSVuser> listOK, String message) {
        for (CSVuser go: listOK) {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(go.getContactValue());
            email.setSubject("EMERGENCY NOTIFICATION SYSTEM");
            email.setText(message);
            javaMailSender.send(email);
        }
        System.out.println("senttt");
        return "SENTTT";
    }
}