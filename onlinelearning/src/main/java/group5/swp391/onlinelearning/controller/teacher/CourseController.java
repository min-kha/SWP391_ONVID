package group5.swp391.onlinelearning.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import org.springframework.web.multipart.MultipartFile;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.mapper.CourseMapper;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOEdit;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.service.ILessonService;
import group5.swp391.onlinelearning.service.ITopicService;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@RequestMapping("/teacher/course")
@Controller
public class CourseController {
    // CRUD
    @Autowired
    CourseService courseService;
    @Autowired
    ITopicService topicService;

    @Autowired
    ILessonService lessionService;

    @Autowired
    CourseMapper mapper;
    @Autowired
    ThymeleafBaseCRUD thymeleafBaseCRUD;

    // TODO: remove req nhá
    @Autowired
    IUserService userService;

    @GetMapping("/list")
    public String getCourseList(Model model, HttpSession req) {
        // TODO: remove user service
        User user = userService.getUserById(70);
        req.setAttribute("user", user);
        // Check role access site
        if (user.getRole() != 1)
            return "AccessDenied";
        // Create List course of teacher and send to front end
        List<CourseDTOTeacher> courses = courseService.getCourseDTOTeacherList();
        String title = "Course List";
        // Throw data to base front end
        thymeleafBaseCRUD.setBaseForList(model, courses, title);
        return "teacher/course/list";
    }

    @GetMapping("/create")
    public String getCreateCourse(Model model, HttpSession req) {
        // Check role access site
        User user = (User) req.getAttribute("user");
        if (user.getRole() != 1)
            return "AccessDenied";
        // create a new model
        model.addAttribute("errorFormat", "");
        model.addAttribute("course", new CourseDTOAdd());
        model.addAttribute("topics", topicService.getTopics());
        return "teacher/course/add";
    }

    @PostMapping("/create")
    public String addCourse(@Valid @ModelAttribute("course") CourseDTOAdd courseDTOAdd, HttpServletRequest req,
            @RequestParam("image") MultipartFile image, BindingResult result, Model model)
            throws IOException, ServletException {
        if (courseDTOAdd.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            result.rejectValue("price", "error.price.invalid", "price must be greater than zero");
        }
        // check error
        if (result.hasErrors()) {
            model.addAttribute("errorFormat", "");
            model.addAttribute("topics", topicService.getTopics());
            return "teacher/course/add";
        }
        // get link image
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("image");
        fileName = filePart.getSubmittedFileName();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
            String relativePath = "\\src\\main\\resources\\static\\image";

            String filePath = projectPath + relativePath + File.separator + fileName;
            Path path = Paths.get(filePath);

            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("error");
            }
            // fileName is not valid ( != jpg and png files)
        } else {
            model.addAttribute("errorFormat", "File Name must format same .jpg or .png");
            model.addAttribute("course", new CourseDTOAdd());
            model.addAttribute("topics", topicService.getTopics());
            return "teacher/course/add";
        }

        // set link to model directory
        courseDTOAdd.setImageLink(fileName);
        int topicId = Integer.parseInt(req.getParameter("topic"));
        courseDTOAdd.setTopic_id(topicId);
        // create a new Course
        courseService.createCourse(courseDTOAdd);
        // redirect to course list
        return "redirect:/teacher/course/list";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateCourse(Model model, @PathVariable @NotNull Integer id, HttpSession req) {
        // Check role access site
        User user = (User) req.getAttribute("user");
        if (user.getRole() != 1)
            return "AccessDenied";
        // check course exit
        Course course = courseService.getCourseById(id);
        if (course == null)
            return "404";
        // check owner course
        if (!courseService.checkCourseOwner(id))
            return "AccessDenied";
        // check published course, submited, progeressing
        if (course.getStatus() == 3 || course.getStatus() == 0 || course.getStatus() == 1)
            return "AccessDenied";
        CourseDTOEdit courseDTEdit = mapper.courseToCourseDtoEdit(course);
        model.addAttribute("errorFormat", "");
        model.addAttribute("course", courseDTEdit);
        model.addAttribute("topics", topicService.getTopics());
        return "teacher/course/edit";
    }

    @PostMapping("/edit")
    public String postUpdateCourse(@Valid @ModelAttribute CourseDTOEdit course, BindingResult bindingResult,
            HttpServletRequest req,
            Model model) throws IOException, ServletException {
        if (course.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            bindingResult.rejectValue("price", "error.price.invalid", "price must be greater than zero");
        }
        // check error status
        // TODO: Chưa bắn đc lỗi
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorFormat", "");
            model.addAttribute("course", course);
            model.addAttribute("topics", topicService.getTopics());
            return "teacher/course/edit";
        }
        // get link image and set link image
        // get link image
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("image");
        fileName = filePart.getSubmittedFileName();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
            String relativePath = "\\src\\main\\resources\\static\\image";

            String filePath = projectPath + relativePath + File.separator + fileName;
            Path path = Paths.get(filePath);

            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Error");
            }
            course.setImageLink(fileName);
            // fileName is not valid ( != jpg and png files)
        } else

        {
            model.addAttribute("errorFormat", "Image in not correct");
            model.addAttribute("course", course);
            model.addAttribute("topics", topicService.getTopics());
            return "teacher/course/edit";
        }
        // set other parameters and save them
        int topic_id = Integer.parseInt(req.getParameter(
                "topic"));
        course.setTopic_id(topic_id);
        courseService.updateCourse(course);
        return "redirect:/teacher/course/detail/" + course.getId();
    }

    @GetMapping("/delete/{id}")
    public String getDeleteCourse(@PathVariable @NotNull Integer id, HttpSession req) {
        // Check role access site
        User user = (User) req.getAttribute("user");
        if (user.getRole() != 1)
            return "AccessDenied";
        // check course exit
        Course course = courseService.getCourseById(id);
        if (course == null)
            return "404";
        // check owner course
        if (!courseService.checkCourseOwner(id))
            return "AccessDenied";
        // check published course, submited, progeressing
        if (course.getStatus() == 3 || course.getStatus() == 0 || course.getStatus() == 1)
            return "AccessDenied";
        courseService.deleteCourse((int) id);
        return "redirect:/teacher/course/list";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable @NotNull Integer id, Model model, HttpSession req) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "teacher/course/detail";
    }

    @GetMapping("/submit/{id}")
    public String getSubmitCourse(Model model, @PathVariable @NotNull Integer id, HttpSession req) {
        // Check role access site
        User user = (User) req.getAttribute("user");
        if (user.getRole() != 1)
            return "AccessDenied";
        // check course exit
        Course course = courseService.getCourseById(id);
        if (course == null)
            return "404";
        // check owner course
        if (!courseService.checkCourseOwner(id))
            return "AccessDenied";
        // check published course, submited, progeressing
        if (course.getStatus() == 3 || course.getStatus() == 0 || course.getStatus() == 1)
            return "AccessDenied";
        // change status of course
        courseService.submitCourse(course);
        return "redirect:/teacher/course/list";
    }
}
