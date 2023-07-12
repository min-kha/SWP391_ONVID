package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@RequestMapping("/admin/users")
@Controller
public class UserController {
    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;
    @Autowired
    private IUserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/index")
    public String getIndex(Model model) {
        List<User> users = userService.getAllUsers();
        String title = "List users - Admin";
        thymeleafBaseCRUD.setBaseForList(model, users, title);
        return "sample/index";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        UserDTORegisterRequest user = new UserDTORegisterRequest();
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Create Staff - Admin");
        return "sample/create";
    }

    @PostMapping("/create")
    public String postCreate(
            @Valid @ModelAttribute("entity") UserDTORegisterRequest userDTORegisterRequest,
            BindingResult bindingResult,
            Model model) {
        final String title = "Create Staff - Admin";
        User user = new User();
        try {
            if (bindingResult.hasErrors()) {
                thymeleafBaseCRUD.setBaseForEntity(model, userDTORegisterRequest, title);
                return "/sample/create";
            }
            user = modelMapper.map(userDTORegisterRequest, User.class);
            userService.addStaff(user);
        } catch (InvalidInputException e) {
            bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(), e.getMessage());
            thymeleafBaseCRUD.setBaseForEntity(model, user, title);
            return "/sample/create";
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/users/index";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(Model model, @PathVariable @NotNull int id) {
        User user = userService.getUserById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Edit user - Admin");
        return "sample/edit";
    }

    @PostMapping("/edit/{id}")
    public String postEdit(@Valid @ModelAttribute("entity") User user, BindingResult bindingResult,
            Model model) {
        final String title = "Edit User - Admin";
        try {
            if (bindingResult.hasErrors()) {
                thymeleafBaseCRUD.setBaseForEntity(model, user, title);
                return "/sample/edit";
            }
            userService.updateUser(user);
        } catch (InvalidInputException e) {
            bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(), e.getMessage());
            thymeleafBaseCRUD.setBaseForEntity(model, user, title);
            return "/sample/edit";
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/users/index";
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
