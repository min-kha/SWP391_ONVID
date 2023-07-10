package group5.swp391.onlinelearning.controller.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import group5.swp391.onlinelearning.entity.Cart;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.ICartService;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.service.OtpService;

@Controller
public class RegisterController {

    @Autowired
    private IUserService userService;
    @Autowired
    ICartService cartService;
    @Autowired
    OtpService otpService;

    @GetMapping("/register")
    public String getMethodRegister(Model model) {
        model.addAttribute("user", new UserDTORegisterRequest());
        return "student/register/Register";
    }

    @PostMapping("/register")
    public String checkValidAccount(@Valid @ModelAttribute("user") UserDTORegisterRequest userDTORegisterRequest,
            BindingResult bindingResult, HttpServletRequest request) {
        if (userService.getUserByEmail(userDTORegisterRequest.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email.exist", "Email address is duplicated");
        }
        if (!userDTORegisterRequest.getPassword().equals(userDTORegisterRequest.getRePassword())) {
            bindingResult.rejectValue("repassword", "error.repass.notSam", "Repassword is not same with password");
        }
        if (bindingResult.hasErrors()) {
            return "student/register/Register";
        }
        request.setAttribute("userDTORegisterRequest", userDTORegisterRequest);
        return "forward:/send-email/true";
    }

    @PostMapping("/store-account")
    public String storeAccount(HttpSession session) {
        UserDTORegisterRequest userDTORegisterRequest = (UserDTORegisterRequest) session
                .getAttribute("userRegisterSession");
        User user = userService.addUserRegister(userDTORegisterRequest);
        Cart cart = cartService.createCart(user);
        session.invalidate();
        return "redirect:/student/login";
    }
}