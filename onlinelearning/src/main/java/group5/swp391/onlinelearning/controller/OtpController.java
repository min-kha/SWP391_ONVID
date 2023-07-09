package group5.swp391.onlinelearning.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import group5.swp391.onlinelearning.service.OtpService;

@Controller
public class OtpController {
    @Autowired
    OtpService otpService;

    @PostMapping("/check-otp")
    public String checkOtp(HttpServletRequest request, Model model) {
        String otpCodeInput = request.getParameter("otpCodeInput");
        String register = request.getParameter("register");
        boolean checkValidOtp = otpService.checkValidOtp(otpCodeInput, model);
        if (checkValidOtp) {
            if (register.equals("false")) {
                return "redirect:/enter-new-password";
            } else {
                return "forward:/store-account";
            }
        } else {
            return "/student/input-otp";
        }
    }
}
