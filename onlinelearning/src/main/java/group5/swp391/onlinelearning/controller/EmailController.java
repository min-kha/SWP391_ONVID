package group5.swp391.onlinelearning.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.EmailSenderService;

@Controller
public class EmailController {
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/send-email/{register}")
    public String sendEmail(HttpServletRequest request, @PathVariable boolean register, HttpSession session,
            Model model)
            throws Exception {
        if (register) {
            UserDTORegisterRequest userDTORegisterRequest = (UserDTORegisterRequest) request
                    .getAttribute("userDTORegisterRequest");
            emailSenderService.sendEmail(userDTORegisterRequest.getEmail(),
                    "is an code verify ONVID account of you",
                    "The OTP to authenticate your account is: ");
            session.setAttribute("userRegisterSession", userDTORegisterRequest);
            long otpCreationTime = System.currentTimeMillis();
            session.setAttribute("otpCreationTime", otpCreationTime);
            return "/student/input-otp";
        } else {
            User user = (User) session.getAttribute("userForgotPassword");
            emailSenderService.sendEmail(user.getEmail(),
                    "is your ONVID account password recovery code",
                    "OTP to recover your account is: ");
            long otpCreationTime = System.currentTimeMillis();
            session.setAttribute("otpCreationTime", otpCreationTime);
            model.addAttribute("register", false);
            return "/student/input-otp";
        }
    }
}
