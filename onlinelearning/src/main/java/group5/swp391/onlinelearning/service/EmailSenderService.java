package group5.swp391.onlinelearning.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.utils.SHA1;
import group5.swp391.onlinelearning.utils.NumberUtils;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private HttpSession session;

    public void sendEmail(String email, String mailSubjectInput, String mailContentInput) throws Exception {
        int otp = NumberUtils.generateRandomNumber();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("onvid0505@gmail.com");
        message.setTo(email);
        String mailSubject = otp + " " + mailSubjectInput;
        String mailContent = mailContentInput;
        mailContent += otp;

        message.setSubject(mailSubject);
        message.setText(mailContent);
        mailSender.send(message);
        String otpString = String.valueOf(otp);
        String otpEncrypt = SHA1.toSHA1(otpString);
        session.setAttribute("otp", otpEncrypt);
    }
}
