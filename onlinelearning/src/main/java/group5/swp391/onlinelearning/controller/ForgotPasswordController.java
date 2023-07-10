package group5.swp391.onlinelearning.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.utils.SHA1;

@Controller
public class ForgotPasswordController {
    @Autowired
    IUserService userService;

    @GetMapping("/forgot-password")
    public String getForgotPassword() {
        return "student/login/enter-email-forgot-password";
    }

    @PostMapping("/check-valid-email")
    public String checkValidEmail(@RequestParam("email") String email, Model model, HttpSession session) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            session.setAttribute("userForgotPassword", user);
            return "forward:/send-email/false";
        } else {
            model.addAttribute("errorEmail", "Email does not exist");
            return "student/login/enter-email-forgot-password";
        }
    }

    @GetMapping("/enter-new-password")
    public String enterNewPassword() {
        return "student/enter-new-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("password") String password,
            @RequestParam("re-password") String re_password, Model model, HttpSession session) {
        if (!password.equals(re_password)) {
            model.addAttribute("notSamePassword", "password and re-password must be the same");
            return "/student/enter-new-password";
        } else {
            User user = (User) session.getAttribute("userForgotPassword");
            user.setPassword(SHA1.toSHA1(password));
            userService.changePassword(user);
            session.invalidate();
            return "redirect:/student/login";
        }
    }
}
