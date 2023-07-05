package group5.swp391.onlinelearning.controller.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.service.Impl.CourseService;

@Controller
@RequestMapping("/student")
public class HomeController {
    @Autowired
    CourseService courseService;

    @GetMapping("/home")
    public String getStudentHome(Model model) {
        List<CourseDtoHomeDetail> courses = courseService.getAllCourseDtoHomeDetails();
        model.addAttribute("courses", courses);
        return "student/home/list-products";
    }
}
