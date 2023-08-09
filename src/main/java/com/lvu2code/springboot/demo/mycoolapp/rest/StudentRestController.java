package com.lvu2code.springboot.demo.mycoolapp.rest;

import com.lvu2code.springboot.demo.mycoolapp.dto.EmailRequest;
import com.lvu2code.springboot.demo.mycoolapp.entity.StudentData;
import com.lvu2code.springboot.demo.mycoolapp.service.StudentService;
import com.lvu2code.springboot.demo.mycoolapp.service.SendGridEmailService;
import com.lvu2code.springboot.demo.mycoolapp.utility.Utitlity;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SendGridEmailService SendGridEmailService;

    private List<StudentData> theStudents;

    // define @PostConstruct to load the student data ... only once!
    @PostConstruct
    public void loadData() {
        System.out.println("@PostConstruct Working---");
        theStudents = new ArrayList<>();

        theStudents.add(new StudentData("Poornima", "Patel"));
        theStudents.add(new StudentData("Mario", "Rossi"));
        theStudents.add(new StudentData("Mary", "Smith"));
    }

    // define endpoint for "/students" - return a list of students

    @GetMapping("/students")
    public List<StudentData> getStudents() {

        return  theStudents;
    }

    // define endpoint or "/students/{studentId}" -return student at index

    @GetMapping("/students/{studentId}")
    public StudentData getStudent(@PathVariable int studentId) {

        // just index into the list ... keep it simple for now

        // check the studentId again list size

        if ( (studentId >= theStudents.size()) || (studentId <0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

         return theStudents.get(studentId);
    }

//    @GetMapping("/forgot_password")
//    public String showForgotPasswordForm(Model model){
//        model.addAttribute("pageTitle", "Forgot Password");
//        return "forgot_password_form";
//    }

//    public void sendMailSample() {
//        // Create the Spring application context
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MailConfig.class);
//
//        // Get the EmailService bean
//        EmailService emailService = context.getBean(EmailService.class);
//
//        // Send a simple email
//        String to = "recipient@example.com";
//        String subject = "Hello from Spring Mail";
//        String text = "This is a test email sent using Spring Mail.";
//        emailService.sendSimpleEmail(to, subject, text);
//
//        // Close the application context
//        context.close();
//    }

    @PostMapping("/forgot_password")
//    public String processForgotPasswordForm(@RequestBody Map<String, String> data){
    public String processForgotPasswordForm(@RequestBody Map<String, String> data, HttpServletRequest request){

//        String email = request.getParameter("email");
        String email = data.get("email");
        String token = RandomString.make(45);//varchar length

        System.out.println("Email: " + email);
        System.out.println("Token: " + token);

        try{
            studentService.updateResetPasswordToken(token, email);

            // generate reset password link
            String resetPasswordLink = Utitlity.getSiteURL(request)+ "/reset_password?token=" + token;
            System.out.println("resetPasswordLink = "+ resetPasswordLink);

            // send email
            this.sendEmail(email, resetPasswordLink);

        }catch (StudentNotFoundException e){
            e.printStackTrace();
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }

        return "forgot_password_form";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello, </p>"
                + "You have requested to reset your password."
                + "Click the link below to change your password:"
                + "<a href=\\" +resetPasswordLink+ "\\>Change my password</a>"
                + "Ignore this email if you do remember your password, or you have not made the request.";

        helper.setSubject(content);
        helper.setText(content, true);

        mailSender.send(message);
    }


    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        // You can create an EmailRequest class with "to", "subject", and "content" properties
        SendGridEmailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getContent());
    }

}
