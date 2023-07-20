package group5.swp391.onlinelearning.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import group5.swp391.onlinelearning.utils.SHA1;

@Service
public class OtpService {
    @Autowired
    private HttpSession session;

    public boolean checkValidOtp(String userInputOTP, Model model) {
        long otpCreationTime = (long) session.getAttribute("otpCreationTime");
        long currentTime = System.currentTimeMillis();
        long elapsedTimeInSeconds = (currentTime - otpCreationTime) / 1000;
        int otpTimeout = 300;

        if (elapsedTimeInSeconds >= otpTimeout) {
            session.invalidate();
            model.addAttribute("OtpCodeTimeOut",
                    "OTP code has expired, please enter the code within 5 minutes from the time the code was sent");
            return false;
        } else {
            String otpCode = session.getAttribute("otp").toString();
            String userInputOTPEncrypt = SHA1.toSHA1(userInputOTP);

            if (otpCode != null && otpCode.equals(userInputOTPEncrypt)) {
                return true;
            } else {
                model.addAttribute("OtpCodeNotTrue", "OTP code is incorrect");
                return false;
            }
        }
    }
}
