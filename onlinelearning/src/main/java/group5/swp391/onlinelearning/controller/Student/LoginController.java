package group5.swp391.onlinelearning.controller.student;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.IUserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class LoginController {
    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String getStudentLogin(Model model) {
        model.addAttribute("student", new UserDTORegisterRequest());
        return "login";
    }

    @PostMapping("login")
    public String postStudentLogin(@Valid @ModelAttribute("student") UserDTOLoginRequest student,
            BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("EnterFieldError", "Login failed");
            return "login";
        } else {
            User studentRes = userService.loginStudent(student, model);
            if (studentRes != null) {
                session.setAttribute("studentSession", studentRes);
                return "redirect:/student/home";
            } else {
                model.addAttribute("loginError", "Login failed");
                return "login";
            }
        }
    }
}
