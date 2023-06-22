package group5.swp391.onlinelearning.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import group5.swp391.onlinelearning.model.user.dto.UserDTORegisterRequest;
import lombok.val;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String getMethodRegister(Model model) {
        model.addAttribute("user", new UserDTORegisterRequest());
        return "Register";
    }

    @PostMapping("/register")
    public String postMethodRegister(@Valid @ModelAttribute("user") UserDTORegisterRequest userDTORegisterRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Register";
        }

        // xly du lieu

        return "redirect:/login";
    }

}
