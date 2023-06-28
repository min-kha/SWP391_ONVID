package group5.swp391.onlinelearning.controller.Student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.service2.Impl.CourseServiceImpl;

@Controller
@RequestMapping("/student")
public class HomeController {
    @Autowired
    CourseServiceImpl courseService;

    @GetMapping("/home")
    public String getStudentHome(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "home-student";
    }
}
