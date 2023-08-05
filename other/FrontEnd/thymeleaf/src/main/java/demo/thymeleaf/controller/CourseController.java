package demo.thymeleaf.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import demo.thymeleaf.model.Course;
import demo.thymeleaf.model.Student;

@Controller
public class CourseController {
        @GetMapping("course/index")
        public String getIndex(Model model) {
                List<Course> courses = new Course().toList();
                model.addAttribute("title", "List courses hihi");
                model.addAttribute("entities", courses);
                List<Field> fields = Arrays.asList(Course.class.getDeclaredFields());
                model.addAttribute("fields", fields);
                return "course/list-products";
        }
}
