package group5.swp391.onlinelearning.controller.student;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.IUserService;

@Controller
public class RegisterController {

    @Autowired
    private IUserService userService;

    @GetMapping("/register")
    public String getMethodRegister(Model model) {
        model.addAttribute("user", new UserDTORegisterRequest());
        return "Register";
    }

    @PostMapping("/register")
    public String postMethodRegister(@Valid @ModelAttribute("user") UserDTORegisterRequest userDTORegisterRequest,
            BindingResult bindingResult) {
        if (userService.getUserByEmail(userDTORegisterRequest.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email.exist", "Email address is duplicated");
        }
        if (!userDTORegisterRequest.getPassword().equals(userDTORegisterRequest.getRePassword())) {
            bindingResult.rejectValue("rePass", "error.repass.notSam", "Repassword is not same with password");
        }
        if (bindingResult.hasErrors()) {
            return "Register";
        }
        userService.addUserRegister(userDTORegisterRequest);
        return "redirect:/student/home";
    }

}