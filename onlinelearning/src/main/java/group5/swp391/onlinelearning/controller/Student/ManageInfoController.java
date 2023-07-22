package group5.swp391.onlinelearning.controller.Student;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "/student/info/student-detail";
    }

    @GetMapping("/information/edit")
    public String getEditInfomationByStudent(HttpSession session, Model model) {
        User student = (User) session.getAttribute("studentSession");
        UserDto studentDto = userService.map(student);
        model.addAttribute("studentDto", studentDto);
        return "/student/info/student-edit";
    }

    @PostMapping("/information/edit")
    public String postEditInfomationByStudent(HttpSession session, @RequestParam("nameChange") String nameChange)
            throws Exception {
        User student = (User) session.getAttribute("studentSession");
        student.setName(nameChange);
        userService.updateUser(student);
        return "redirect:/student/information";
    }

}
