package tn.esprit.user.services.Implementations;

import tn.esprit.user.entities.User;
import tn.esprit.user.entities.VerificationToken;
import tn.esprit.user.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
public class EmailService {

    private final UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    public EmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendPasswordChangeEmail(User user, VerificationToken verificationToken)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "elmejri.sami@gmail.com";
        String senderName = "Courzelo";
        String subject = "Password Change Request";
        String content = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Email Template</title>" +
                "<!-- AdminLTE CSS -->" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css\">" +
                "<!-- Google Fonts -->" +
                "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">" +
                "<style>" +
                "body { font-family: 'Roboto', sans-serif; }" +
                ".email-container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".btn { display: inline-block; font-weight: 400; color: #fff; text-align: center; vertical-align: middle; user-select: none; background-color: #007bff; border: 1px solid transparent; padding: 0.375rem 0.75rem; font-size: 1rem; line-height: 1.5; border-radius: 0.25rem; transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out; }" +
                ".btn:hover { background-color: #0056b3; color: #fff; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"email-container\">" +
                "<div class=\"text-center\">" +
                "<img src=\"https://courzelo.com/assets/Notable.png\" alt=\"Courzelo Logo\" width=\"100\">" +
                "</div>" +
                "<h2 class=\"text-center\">Hello [[name]],</h2>" +
                "<p>We've received a request to change your password. Please click the button below to proceed:</p>" +
                "<div class=\"text-center\">" +
                "<a href=\"[[URL]]\" class=\"btn btn-primary\">Change Password</a>" +
                "</div>" +
                "<div class=\"footer\">" +
                "If you need to report an error, please contact us at <a href=\"https://www.courzelo.com\">www.courzelo.com</a>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName() + " " + user.getLastName());
        String changeURL = "localhost:4200" + "/recover-password?token=" + verificationToken.getToken();

        content = content.replace("[[URL]]", changeURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public void sendVerificationEmail(User user,VerificationToken verificationToken)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "elmejri.sami@gmail.com";
        String senderName = "Courzelo";
        String subject = "Please verify your registration";
        String content = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Email Template</title>" +
                "<!-- AdminLTE CSS -->" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css\">" +
                "<!-- Google Fonts -->" +
                "<link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap\" rel=\"stylesheet\">" +
                "<style>" +
                "body { font-family: 'Roboto', sans-serif; }" +
                ".email-container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".btn { display: inline-block; font-weight: 400; color: #fff; text-align: center; vertical-align: middle; user-select: none; background-color: #007bff; border: 1px solid transparent; padding: 0.375rem 0.75rem; font-size: 1rem; line-height: 1.5; border-radius: 0.25rem; transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out; }" +
                ".btn:hover { background-color: #0056b3; color: #fff; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"email-container\">" +
                "<div class=\"text-center\">" +
                "<img src=\"https://courzelo.com/assets/Notable.png\" alt=\"Courzelo Logo\" width=\"100\">" +
                "</div>" +
                "<h2 class=\"text-center\">Hello [[name]],</h2>" +
                "<p>Thank you for registering with Courzelo. Please click the button below to verify your email address:</p>" +
                "<div class=\"text-center\">" +
                "<a href=\"[[URL]]\" class=\"btn btn-primary\">Verify Email Address</a>" +
                "</div>" +
                "<div class=\"footer\">" +
                "If you need to report an error, please contact us at <a href=\"https://www.courzelo.com\">www.courzelo.com</a>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName() + " " + user.getLastName());
        String verifyURL = "localhost:4200" + "/verify?code=" + verificationToken.getToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public void sendVerificationCode(User user,VerificationToken verificationToken)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "elmejri.sami@gmail.com";
        String senderName = "Courzelo";
        String subject = "Please verify your registration";
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Content</title>\n" +
                "    <!-- AdminLTE CSS -->\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/admin-lte/3.1.0/css/adminlte.min.css\">\n" +
                "    <style>\n" +
                "        body, html {\n" +
                "            height: 100%;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .wrapper {\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "            justify-content: center;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "        .email-content {\n" +
                "            max-width: 600px; /* Adjust max-width as needed */\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"wrapper\">\n" +
                "        <div class=\"email-content\">\n" +
                "<div class=\"text-center\">" +
                "<img src=\"https://courzelo.com/assets/Notable.png\" alt=\"Courzelo Logo\" width=\"100\">" +
                "</div>" +
                "            <!-- Main content -->\n" +
                "            <section class=\"content\">\n" +
                "                <div class=\"container-fluid\">\n" +
                "                    <div class=\"card\">\n" +
                "                        <div class=\"card-header\">\n" +
                "                            <h3 class=\"card-title\">Device Verification</h3>\n" +
                "                        </div>\n" +
                "                        <!-- /.card-header -->\n" +
                "                        <div class=\"card-body\">\n" +
                "                            <p>Dear [[name]],</p>\n" +
                "                            <p>Your verification code is: <strong>[[verificationCode]]</strong></p>\n" +
                "                            <p>Thank you,</p>\n" +
                "                            <p>Courzelo</p>\n" +
                "                        </div>\n" +
                "                        <!-- /.card-body -->\n" +
                "                    </div>\n" +
                "                    <!-- /.card -->\n" +
                "                </div>\n" +
                "                <!-- /.container-fluid -->\n" +
                "            </section>\n" +
                "            <!-- /.content -->\n" +
                "        </div>\n" +
                "        <!-- /.email-content -->\n" +
                "    </div>\n" +
                "    <!-- /.wrapper -->\n" +
                "\n" +
                "<div class=\"footer\">" +
                "If you need to report an error, please contact us at <a href=\"https://www.courzelo.com\">www.courzelo.com</a>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName() + " " + user.getLastName());
        content = content.replace("[[verificationCode]]", verificationToken.getToken());

        helper.setText(content, true);

        mailSender.send(message);
    }
}