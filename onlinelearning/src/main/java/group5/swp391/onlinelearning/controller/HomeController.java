package group5.swp391.onlinelearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String getHome() {
        return "home-student";
    }

    // @RequestMapping(value = "/home", method = RequestMethod.GET)
    // public String getHome() {
    // return "home-student";
    // }
}
