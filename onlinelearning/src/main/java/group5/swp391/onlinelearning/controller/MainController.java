package group5.swp391.onlinelearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("meo")
    public String login() {
        return "Register";
    }

}
