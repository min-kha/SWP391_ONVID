package group5.swp391.onlinelearning.controller.guest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "GuestLoginController")
public class LoginController {
    @GetMapping("/login")
    public String getLoginGuest() {
        return "/guest/login/choose-login";
    }
}
