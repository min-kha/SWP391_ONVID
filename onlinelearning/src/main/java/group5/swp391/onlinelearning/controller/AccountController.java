package group5.swp391.onlinelearning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.model.user.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.service2.IUserService;
import group5.swp391.onlinelearning.service2.Impl.UserService;

@Controller
public class AccountController {

    @Autowired
    private IUserService userService = new UserService();

    @GetMapping("/account")
    public String getAccountManage(Model model) {
        model.addAttribute("listAccount", userService.getAllUserDTOAccountRequest());
        return "ManageAccount";
    }

    @PostMapping("/account")
    public String postAccountManage(@RequestParam("id") String sId) {

        int id = Integer.parseInt(sId);
        userService.changeStatus(id);

        return "redirect:account";
    }

    @GetMapping("/create-staff")
    public String getCreateStaff(Model model) {
        model.addAttribute("staff", new StaffDTOCreate());
        return "CreateStaff";
    }

    @PostMapping("/create-staff")
    public String postCreateUser(@Valid @ModelAttribute("staff") StaffDTOCreate staffDTOCreate,
            BindingResult bindingResult,
            Model model) {
        if (userService.getUserByEmail(staffDTOCreate.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.duplicate", "Email đã tồn tại trong hệ thống");
        }

        if (bindingResult.hasErrors()) {

            return "CreateStaff";
        } else
            userService.addStaff(staffDTOCreate);
        return "redirect:account";
    }
}
