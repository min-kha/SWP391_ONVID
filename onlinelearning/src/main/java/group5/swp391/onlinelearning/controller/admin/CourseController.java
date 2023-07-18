package group5.swp391.onlinelearning.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
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
import group5.swp391.onlinelearning.entity.CourseReview;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.admin.UserDto;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller("CourseAdminController")
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

    @GetMapping("/details/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        Course course = courseService.getCourseById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, course, "Detail Course - Admin");
        return "admin/course/detail";
    }

    @GetMapping("/review/{id}")
    public String getReview(Model model, @PathVariable @NotNull int id, HttpSession session) throws Exception {
        String title = "Review Course - Admin";
        try {
            Course course = courseService.getCourseById(id);
            User user = (User) session.getAttribute("user");

            if (course.getStatus() == 0) {
                CourseReview startReview = CourseReview.builder()
                        .time(new Date())
                        .comment("Start review course!")
                        .status(1)
                        .course(course)
                        .staff(user)
                        .build();
                course.getCourseReviews().add(startReview);
                course.setStatus(1); // set status to In processing
                courseService.updateCourse(course);
            }
            thymeleafBaseCRUD.setBaseForEntity(model, course, title);
            thymeleafBaseCRUD.setBaseForList(model, (List) course.getCourseReviews(), title);
        } catch (Exception e) {
            // handle exception
        }
        return "admin/course/review";
    }

    @PostMapping("/review/{id}")
    public String postReview(Model model, @PathVariable @NotNull int id, @RequestParam String approve) {
        Course course = courseService.getCourseById(id);
        try {
            if (approve.equals("true")) {
                course.setStatus(3); // set status to approved
                courseService.updateCourse(course);

            }
            if (approve.equals("false")) {
                course.setStatus(2); // set status to rejected
                courseService.updateCourse(course);
            }
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }
        return "redirect:/admin/courses/details/{id}";
    }

    @GetMapping("/deactive/{id}")
    public String getDeactive(Model model, @PathVariable @NotNull int id) {
        try {
            Course course = courseService.getCourseById(id);
            thymeleafBaseCRUD.setBaseForEntity(model, course, "Confirm deactive course - Admin");
        } catch (Exception e) {
            return "/error";
        }
        return "admin/course/deactive";
    }

    @GetMapping("/active/{id}")
    public String getActive(Model model, @PathVariable @NotNull int id) {
        try {
            if (courseService.getCourseById(id).getStatus() == -2) {
                courseService.changeStatus(id);
            }
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/courses/index";
    }

    @PostMapping("/deactive/{id}")
    public String postDelete(Model model, @PathVariable @NotNull int id) {
        try {
            if (courseService.getCourseById(id).getStatus() != -2) {
                courseService.changeStatus(id);
            }
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/users/index";
    }
}
