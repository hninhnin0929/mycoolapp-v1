package com.lvu2code.springboot.demo.mycoolapp.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendGridEmailService {

    @Autowired
    private SendGrid sendGrid;

    public void sendEmail(String to, String subject, String content) {
        Email from = new Email("your-email@example.com"); // Replace with your email address
        Email toEmail = new Email(to);
        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, toEmail, emailContent);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println("Email sent. Status code: " + response.getStatusCode());
        } catch (Exception ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }
    }
}
