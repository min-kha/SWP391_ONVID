package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.Impl.UserService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@RequestMapping("/admin/users")
@Controller
public class UserController {
    @Autowired
    ThymeleafBaseCRUD thymeleafBaseCRUD;
    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String getIndex(Model model) {
        List<User> users = userService.getAllUsers();
        String title = "List users - Admin";
        thymeleafBaseCRUD.setBaseForList(model, users, title);
        return "sample/index";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        User user = new User();
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Create User - Admin");
        return "sample/create";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(Model model, @PathVariable @NotNull int id) {
        User user = userService.getUserById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Edit user - Admin");
        return "sample/edit";
    }

    @GetMapping("/delete/{id}")
    public String getDelete(Model model, @PathVariable @NotNull int id) {
        User user = userService.getUserById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Confirm delete user - Admin");
        return "sample/delete";
    }

    @GetMapping("/details/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        User user = userService.getUserById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Detail user - Admin");
        return "sample/detail";
    }
}
