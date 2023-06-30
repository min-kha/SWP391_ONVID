package group5.swp391.onlinelearning.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.model.dto.CourseDtoDetail;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.service.Impl.CourseService;

@Controller(value = "CourseStudentController")
@RequestMapping("/student/course")
public class CourseController {
    @Autowired
    CourseService courseServiceImpl;

    @GetMapping("/detail/{courseId}")
    public String detail(Model model, @PathVariable Integer courseId) {
        CourseDtoDetailStudent course = courseServiceImpl.getCourseDetailForStudentById(courseId);
        model.addAttribute("course", course);
        return "/student/course/detail";
    }
}
