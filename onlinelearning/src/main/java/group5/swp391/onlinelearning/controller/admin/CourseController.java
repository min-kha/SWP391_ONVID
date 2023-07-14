package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller("AdminCourseController")
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/index")
    public String getIndex(Model model) {
        List<Course> courses = courseService.getAllCourses();
        String title = "List Courses - Admin";
        thymeleafBaseCRUD.setBaseForList(model, courses, title);
        return "admin/course/index";
    }

    // @GetMapping("/edit/{id}")
    // public String getEdit(Model model, @PathVariable @NotNull int id) {
    // Course course = courseService.getCourseById(id);
    // thymeleafBaseCRUD.setBaseForEntity(model, course, "Edit Course - Admin");
    // return "sample/edit";
    // }

    // @PostMapping("/edit/{id}")
    // public String postEdit(@Valid @ModelAttribute("entity") Course course,
    // BindingResult
    // bindingResult,
    // Model model) {
    // final String title = "Edit Course - Admin";
    // try {
    // if (bindingResult.hasErrors()) {
    // thymeleafBaseCRUD.setBaseForEntity(model, course, title);
    // return "/sample/edit";
    // }
    // courseService.updateCourse(course);
    // } catch (InvalidInputException e) {
    // bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(),
    // e.getMessage());
    // thymeleafBaseCRUD.setBaseForEntity(model, course, title);
    // return "/sample/edit";
    // } catch (Exception e) {
    // return "/error";
    // }
    // return "redirect:/admin/courses/index";
    // }

    // @PostMapping("/delete/{id}")
    // public String postDelete(Model model, @PathVariable @NotNull int id) {
    // try {
    // courseService.deleteCourse(id);
    // } catch (Exception e) {
    // return "/error";
    // }
    // return "redirect:/admin/courses/index";
    // }

    @GetMapping("/detail/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        Course course = courseService.getCourseById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, course, "Detail Course - Admin");
        return "admin/course/detail";
    }

    @GetMapping("/review/{id}")
    public String getReview(Model model, @PathVariable @NotNull int id) throws Exception {
        try {
            Course course = courseService.getCourseById(id);
            if (course.getStatus() == 0) {
                // TODO: course.setStaff(//staff in session);
                course.setStatus(1); // set status to In processing
                courseService.updateCourse(course);
            }
            thymeleafBaseCRUD.setBaseForEntity(model, course, "Review Course - Admin");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "admin/course/review";
    }

    @PostMapping("/review/{id}")
    public String postReview(Model model, @PathVariable @NotNull int id, @RequestParam String approve) {
        Course course = courseService.getCourseById(id);
        try {
            if (approve.equals("true")) {
                course.setStatus(2); // set status to approved
                courseService.updateCourse(course);

            }
            if (approve.equals("false")) {
                course.setStatus(3); // set status to rejected
                courseService.updateCourse(course);
            }
            thymeleafBaseCRUD.setBaseForEntity(model, course, "Detail Course - Admin");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "redirect:/admin/courses/detail/{id}";
    }
}
