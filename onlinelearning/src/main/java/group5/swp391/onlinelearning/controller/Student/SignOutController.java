package group5.swp391.onlinelearning.controller.Student;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignOutController {
    @GetMapping("/student/sign-out")
    public String studentSignOut(HttpSession session) {
        session.removeAttribute("studentSession");
        session.removeAttribute("cartStudentSession");
        return "redirect:/student/login";
    }
}
