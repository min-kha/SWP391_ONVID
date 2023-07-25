package group5.swp391.onlinelearning.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.CourseReview;
import group5.swp391.onlinelearning.entity.User;
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
    public String getIndex(Model model, @RequestParam(required = false) String reviewMode) {
        List<Course> courses;
        if (reviewMode != null && reviewMode.equals("true")) {
            courses = courseService.getReviewCourses();
        } else {
            courses = courseService.getAllCourses();
        }
        String title = "List Courses - Admin";
        thymeleafBaseCRUD.setBaseForList(model, courses, title);
        return "admin/course/index";
    }

    @GetMapping("/detail/{id}")
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
            model.addAttribute("courseReview", new CourseReview());
            thymeleafBaseCRUD.setBaseForEntity(model, course, title);
        } catch (Exception e) {
            // handle exception
        }
        return "admin/course/review";
    }

    @PostMapping("/review/{courseId}")
    public String postReview(Model model, @Valid @ModelAttribute CourseReview courseReview,
            @PathVariable @NotNull int courseId, BindingResult result, HttpSession session) {
        Course course = courseService.getCourseById(courseId);
        User user = (User) session.getAttribute("user");
        try {
            if (result.hasErrors()) {
                thymeleafBaseCRUD.setBaseForEntity(model, course, "Review Course - Admin");
                return "admin/course/review";
            }
            courseReview.setTime(new Date());
            courseReview.setStaff(user);
            courseReview.setCourse(course);
            // add new review for course
            course.getCourseReviews().add(courseReview);
            // update new status for course after review
            course.setStatus(courseReview.getStatus());
            courseService.updateCourse(course); // update course and update course review
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }
        return "redirect:/admin/courses/review/{courseId}";
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
        return "redirect:/admin/courses/index";
    }
}
