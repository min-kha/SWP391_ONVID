package group5.swp391.onlinelearning.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.Service.Impl.CourseServiceImpl;
import group5.swp391.onlinelearning.model.CourseDTO;

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
        return "detail";
    }

    @GetMapping("/list")
    public String getCourseList(Model model, @PathVariable @NotNull Integer id) {
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "list";
    }

}
