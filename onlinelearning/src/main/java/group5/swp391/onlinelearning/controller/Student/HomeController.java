package group5.swp391.onlinelearning.controller.student;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.service.impl.CourseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/student")
public class HomeController {
    @Autowired
    CourseService courseService;

    @GetMapping("/home")
    public String getStudentHome(Model model, HttpSession session) {
        List<CourseDtoHomeDetail> courses = courseService.getAllCourseDtoHomeDetails();
        User student = (User) session.getAttribute("studentSession");
        model.addAttribute("courses", courses);
        model.addAttribute("student", student);
        return "student/home/list-products";
    }

  

}
