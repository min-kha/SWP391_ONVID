package group5.swp391.onlinelearning.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.service.impl.CourseService;

@Controller
@RequestMapping("/student")
public class FeedbackController {
    @Autowired
    CourseService courseService;

    @GetMapping("/course/feedback/{courseId}")
    public String getFeedbackForCourse(Model model, @PathVariable String courseId) {
        int courseIdInt = Integer.parseInt(courseId);
        Course course = courseService.getCourseById(courseIdInt);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        return "student/course/feedback";
    }
}
