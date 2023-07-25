package group5.swp391.onlinelearning.controller.teacher;

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

@Controller
@RequestMapping("/teacher")
public class LoginTeacherController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartService cartService;

    @GetMapping("/login")
    public String getStudentLogin(Model model, RedirectAttributes redirectAttributes) {
        if (model.getAttribute("teacher") == null) {
            model.addAttribute("teacher", new UserDTOLoginRequest());
        }
        return "/teacher/login/login";
    }

    @PostMapping("/login")
    public String postTeacherLogin(@Valid @ModelAttribute("teacher") UserDTOLoginRequest teacher,
            BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "/teacher/login/login";
        } else {
            boolean isProcess = false;
            User teacherRes = userService.loginTeacher(teacher, model, isProcess);
            if (teacherRes != null) {
                session.setAttribute("user", teacherRes);
                // TODO: GO HOME TEACHER
                return "redirect:/teacher/home";
            } else {
                if ((Boolean) model.getAttribute("cVProcess")) {
                    return "/teacher/login/watting";
                }
                model.addAttribute("loginError", "Login failed");
                return "/teacher/login/login";
            }
        }
    }
}
