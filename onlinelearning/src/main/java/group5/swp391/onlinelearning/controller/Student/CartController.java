package group5.swp391.onlinelearning.controller.Student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class CartController {

    @PostMapping("/cart")
    public String getCart(Model model) {
        return "Cart";
    }
}
