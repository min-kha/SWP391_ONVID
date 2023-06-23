package group5.swp391.onlinelearning.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.model.CourseDTO;
import group5.swp391.onlinelearning.service.Impl.CourseServiceImpl;

@RequestMapping("/teacher/course")
@Controller
public class CourseController {
    // CRUD
    @Autowired
    CourseServiceImpl courseService;

    @GetMapping("/detail/{id}")
    public String getCourse(Model model, @PathVariable @NotNull Integer id) {
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "course/detail";
    }

    @GetMapping("/list")
    public String getCourseList(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "course/list";
    }

}
