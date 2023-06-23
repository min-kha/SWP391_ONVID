package group5.swp391.onlinelearning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import group5.swp391.onlinelearning.model.user.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.IUserService;

@Controller
public class RegisterController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/register")
    public String getMethodRegister(Model model) {
        model.addAttribute("listAccout", iUserService.getAllUsers());
        return "Register";
    }

    @PostMapping("/register")
    public String postMethodRegister(@Valid @ModelAttribute("user") UserDTORegisterRequest userDTORegisterRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Register";
        }
        return "Register";
    }
}