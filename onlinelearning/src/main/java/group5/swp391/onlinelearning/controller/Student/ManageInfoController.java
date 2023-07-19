package group5.swp391.onlinelearning.controller.student;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.admin.UserDto;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;


@Controller
@RequestMapping("/student")
public class ManageInfoController {
    @Autowired
    IUserService userService;

    @Autowired
    ThymeleafBaseCRUD thymeleafBaseCRUD;

    @GetMapping("/information")
    public String getInformationByStudent(HttpSession session, Model model) {
        User student = (User) session.getAttribute("studentSession");
        UserDto studentDto = userService.map(student);
        thymeleafBaseCRUD.setBaseForEntity(model, studentDto, "Student profile");
        return "/sample/detail";
    }

    // @PostMapping("/information")
    // public String postInformationByStudent() {

    // }

}
