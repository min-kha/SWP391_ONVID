package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@RequestMapping("/admin/students")
@Controller
public class StudentController {
    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;
    @Autowired
    private IUserService userService;

    @GetMapping("/index")
    public String getIndex(Model model) {
        List<User> users = userService.getAllUsers();
        String title = "List users - Admin";
        thymeleafBaseCRUD.setBaseForList(model, users, title);
        return "sample/index";
    }

    @GetMapping("/delete/{id}")
    public String getDelete(Model model, @PathVariable @NotNull int id) {
        User user = userService.getUserById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Confirm delete user - Admin");
        return "sample/delete";
    }

    @PostMapping("/delete/{id}")
    public String postDelete(Model model, @PathVariable @NotNull int id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/users/index";
    }

    @GetMapping("/details/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        try {
            User user = userService.getUserById(id);
            thymeleafBaseCRUD.setBaseForEntity(model, user, "Detail user - Admin");
        } catch (Exception e) {
            return "/error";
        }
        return "sample/detail";
    }
}
