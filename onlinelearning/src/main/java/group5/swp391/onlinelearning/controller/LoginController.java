package group5.swp391.onlinelearning.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Reactive.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service2.IUserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @Autowired
    private IUserService userService;

    @GetMapping("login1")
    public String getStudentLogin(Model model) {
        model.addAttribute("student", new UserDTORegisterRequest());
        return "login";
    }

    @GetMapping("home-student")
    public String getStudentHome() {
        return "home-student";
    }

    @PostMapping("login")
    public String postStudentLogin(@Valid @ModelAttribute("student") UserDTOLoginRequest student,
            BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            // model.addAttribute("EnterFieldError", "Login failed");
            return "login";
        } else {
            if (userService.loginStudent(student, bindingResult)) {
                session.setAttribute("studentSession", student);
                return "redirect:home";
            } else {
                model.addAttribute("loginError", "Login failed");
                return "login";
            }
        }
    }
}
