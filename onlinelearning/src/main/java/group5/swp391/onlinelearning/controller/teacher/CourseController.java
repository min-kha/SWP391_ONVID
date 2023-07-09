package group5.swp391.onlinelearning.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import group5.swp391.onlinelearning.service.ILessionService;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.service.admin.ITopicService;
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
    ILessionService lessionService;

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
        req.setAttribute("studentSession", user);
        List<CourseDTOTeacher> courses = courseService.getCourseDTOTeacherList();
        String title = "Course List";
        thymeleafBaseCRUD.setBaseForList(model, courses, title);

        return "teacher/course/list";
    }

    @GetMapping("/create")
    public String getCreateCourse(Model model) {
        model.addAttribute("course", new CourseDTOAdd());
        model.addAttribute("topics", topicService.getTopics());
        return "teacher/course/add";
    }

    @PostMapping("/create")
    public String addCourse(@Valid @ModelAttribute("course") CourseDTOAdd courseDTOAdd, HttpServletRequest req,
            @RequestParam("image") MultipartFile image, BindingResult result)
            throws IOException, ServletException {
        if (result.hasErrors()) {
            return "teacher/course/add";
        }
        // String fileName = ("");
        // Part filePart = req.getPart("image");
        // fileName = filePart.getSubmittedFileName();
        // Resource resource = new ClassPathResource("static/image/");
        // String absolutePath = resource.getFile().getAbsolutePath();
        // String filePath = absolutePath + "\\" + fileName;
        // Path path = Paths.get(filePath);
        // InputStream inputStream = filePart.getInputStream();
        // Files.copy(inputStream, path,
        // StandardCopyOption.REPLACE_EXISTING);
        // SWP391_ONVID\onlinelearning\target\classes\static\image
        // SWP391_ONVID\onlinelearning\src\main\resources\static\image
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("image");
        fileName = filePart.getSubmittedFileName();
        String relativePath = "\\onlinelearning\\src\\main\\resources\\static\\image";

        String filePath = projectPath + relativePath + File.separator + fileName;
        Path path = Paths.get(filePath);
        InputStream inputStream = filePart.getInputStream();
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

        courseDTOAdd.setImageLink(fileName);
        int topic_id = Integer.parseInt(req.getParameter("topic"));
        courseDTOAdd.setTopic_id(topic_id);
        courseService.createCourse(courseDTOAdd);
        return "redirect:/teacher/course/list";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateCourse(Model model, @PathVariable @NotNull Integer id) {
        Course course = courseService.getCourseById(id);
        CourseDTOEdit courseDTEdit = mapper.courseToCourseDtoEdit(course);
        model.addAttribute("course", courseDTEdit);
        model.addAttribute("topics", topicService.getTopics());
        return "teacher/course/edit";
    }

    @PostMapping("/edit")
    public String postUpdateCourse(@Valid @ModelAttribute CourseDTOEdit course, HttpServletRequest req,
            BindingResult bindingResult) throws IOException, ServletException {
        if (bindingResult.hasErrors()) {
            return "redirect:/teacher/course/edit/" + course.getId();
        }
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("image");
        fileName = filePart.getSubmittedFileName();
        if (!fileName.equals("")) {
            String relativePath = "\\onlinelearning\\src\\main\\resources\\static\\image";

            String filePath = projectPath + relativePath + File.separator + fileName;
            Path path = Paths.get(filePath);
            InputStream inputStream = filePart.getInputStream();
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            course.setImageLink(fileName);
        }
        int topic_id = Integer.parseInt(req.getParameter("topic"));
        course.setTopic_id(topic_id);
        courseService.updateCourse(course);
        return "redirect:/teacher/course/detail/" + course.getId();
    }

    @GetMapping("/delete/{id}")
    public String getDeleteCourse(@PathVariable @NotNull Integer id) {
        courseService.deleteCourse((int) id);
        return "redirect:../list";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable @NotNull Integer id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "teacher/course/detail";
    }
}
