package group5.swp391.onlinelearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "/AccessDenied";
    }

    @GetMapping("/error")
    public String getError() {
        return "/error";
    }
}
