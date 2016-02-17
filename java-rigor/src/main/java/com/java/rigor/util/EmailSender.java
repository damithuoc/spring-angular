package com.java.rigor.util;

import com.java.rigor.exception.JavaRigorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by sanandasena on 1/27/2016.
 */
@Service
public class EmailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private MailSender mailSender;

    public void sendEmail(String toEmailAddress) throws JavaRigorException {
        LOGGER.info("Entered sendEmail(" + toEmailAddress + ")");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("sajithnandasena.uoc@gmail.com");
        simpleMailMessage.setTo(toEmailAddress);
        simpleMailMessage.setSubject("Test Email");
        simpleMailMessage.setText("Test email");
        mailSender.send(simpleMailMessage);
    }

}
