package group5.swp391.onlinelearning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private IUserService userService;

    @GetMapping("login")
    public String getStudentLogin(Model model) {
        model.addAttribute("student", new UserDTORegisterRequest());
        return "login";
    }

    @PostMapping("student_login")
    public String postStudentLogin(@Valid @ModelAttribute("student") UserDTOLoginRequest student,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("EnterFieldError", "Login failed");
            model.addAttribute("EnterFieldError", "Login failed");
            return "login";
        } else {
            if (userService.loginStudent(student)) {
                return "home-student";
            } else {
                model.addAttribute("loginError", "Login failed");
                return "login";
            }
        }
    }
}
