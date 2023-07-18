package group5.swp391.onlinelearning.controller.admin;

import java.util.ArrayList;
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
import group5.swp391.onlinelearning.model.admin.UserDto;
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
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(userService.map(user));
        }
        String title = "List users - Admin";
        thymeleafBaseCRUD.setBaseForList(model, usersDto, title);
        return "admin/user/index";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        UserDTORegisterRequest user = new UserDTORegisterRequest();
        thymeleafBaseCRUD.setBaseForEntity(model, user, "Create Staff - Admin");
        return "admin/user/create";
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
    public String getEdit(Model model, @PathVariable @NotNull int id) throws Exception {
        User user = userService.getUserById(id);
        var userDto = userService.map(user);
        thymeleafBaseCRUD.setBaseForEntity(model, userDto, "Edit user - Admin");
        return "admin/user/edit";
    }

    @PostMapping("/edit/{id}")
    public String postEdit(@Valid @ModelAttribute("entity") UserDto userDto, BindingResult bindingResult,
            Model model) {
        final String title = "Edit User - Admin";
        User user = new User();
        try {
            user = userService.map(userDto);
            if (bindingResult.hasErrors()) {
                thymeleafBaseCRUD.setBaseForEntity(model, userDto, title);
                return "/sample/edit";
            }
            userService.updateUser(user);
        } catch (InvalidInputException e) {
            bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(), e.getMessage());
            thymeleafBaseCRUD.setBaseForEntity(model, userDto, title);
            return "/sample/edit";
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/users/index";
    }

    @GetMapping("/deactive/{id}")
    public String getDeactive(Model model, @PathVariable @NotNull int id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = userService.map(user);
            thymeleafBaseCRUD.setBaseForEntity(model, userDto, "Confirm deactive user - Admin");
        } catch (Exception e) {
            return "/error";
        }
        return "admin/user/deactive";
    }

    @GetMapping("/active/{id}")
    public String getActive(Model model, @PathVariable @NotNull int id) {
        try {
            if (!Boolean.TRUE.equals(userService.getUserById(id).getStatus())) {
                userService.changeStatus(id);
            }
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/users/index";
    }

    @PostMapping("/deactive/{id}")
    public String postDelete(Model model, @PathVariable @NotNull int id) {
        try {
            if (Boolean.TRUE.equals(userService.getUserById(id).getStatus())) {
                userService.changeStatus(id);
            }
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/users/index";
    }

    @GetMapping("/details/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = userService.map(user);
            thymeleafBaseCRUD.setBaseForEntity(model, userDto, "Detail user - Admin");
        } catch (Exception e) {
            return "/error";
        }
        return "admin/user/detail";
    }
}
