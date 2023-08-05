package group5.swp391.onlinelearning.controller.student;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignOutController {
    @GetMapping("/sign-out")
    public String studentSignOut(HttpSession session) {
        session.removeAttribute("studentSession");
        session.removeAttribute("cartStudentSession");
        session.removeAttribute("user");
        session.removeAttribute("wallet");
        return "redirect:/home/1";
    }
}
