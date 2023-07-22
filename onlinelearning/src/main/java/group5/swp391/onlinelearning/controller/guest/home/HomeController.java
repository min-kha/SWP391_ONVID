package group5.swp391.onlinelearning.controller.guest.home;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.utils.PagingUtils;

@Controller(value = "HomeGuestController")
public class HomeController {
    @Autowired
    CourseService courseService;

    @Autowired
    PagingUtils pagingUtils;

    @GetMapping("/home/{pageChoose}")
    public String getStudentHome(Model model, HttpSession session, @PathVariable String pageChoose) {
        List<CourseDtoHomeDetail> courses = courseService.getAllCourseDtoHomeDetails();
        List<CourseDtoHomeDetail> popularCourses = courseService.getPopularCourse();
        int numberPerPage = 12;
        List<CourseDtoHomeDetail> listOnPage = (List<CourseDtoHomeDetail>) pagingUtils.getPagingList(pageChoose,
                courses, numberPerPage);
        int numberOfPage = pagingUtils.getNumberOfPage(courses, numberPerPage);
        int pageChooseInt = Integer.parseInt(pageChoose);

        List<Integer> listPageNumber = pagingUtils.getListPageNumber(numberOfPage);

        model.addAttribute("courses", listOnPage);
        model.addAttribute("listPageNumber", listPageNumber);
        model.addAttribute("pageChoose", pageChooseInt);
        model.addAttribute("popularCourses", popularCourses);
        return "guest/home/list-products";
    }

}
