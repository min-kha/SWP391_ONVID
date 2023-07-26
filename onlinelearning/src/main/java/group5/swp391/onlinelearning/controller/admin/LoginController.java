package group5.swp391.onlinelearning.controller.admin;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.service.ICartService;
import group5.swp391.onlinelearning.service.IUserService;

@Controller(value = "LoginAdminController")
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartService cartService;

    @GetMapping("/login")
    public String getLogin(Model model, RedirectAttributes redirectAttributes) {
        if (model.getAttribute("user") == null) {
            model.addAttribute("user", new UserDTOLoginRequest());
        }
        return "/admin/auth/login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid @ModelAttribute("user") UserDTOLoginRequest teacher,
            BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "/admin/auth/login";
        } else {
            User userRes = userService.loginStudent(teacher, model);
            if (userRes != null) {
                session.setAttribute("user", userRes);
                session.setAttribute("studentSession", userService.getUserById(1));
                if (cartService.getCartByStudentId(userRes.getId()) == null)
                    cartService.createCart(userRes);
                return "redirect:/admin/home";
            } else {
                model.addAttribute("loginError", "Login failed");
                return "/admin/auth/login";
            }
        }
    }
}
