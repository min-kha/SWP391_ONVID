package group5.swp391.onlinelearning.controller.teacher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.teacher.CourseDTOHomePage;
import group5.swp391.onlinelearning.service.IFeedbackServive;
import group5.swp391.onlinelearning.service.IViewService;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;
import group5.swp391.onlinelearning.service.impl.CourseService;

@Controller(value = "TeacherController")
@RequestMapping("/teacher/")
public class HomeController {
    @Autowired
    IWithdrawalDetailService withdrawalDetailService;

    @Autowired
    IViewService viewService;

    @Autowired
    IFeedbackServive feedbackServive;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CourseService courseService;

    @GetMapping("home")
    public String getHomePage(Model model, HttpSession session) {
        User teacher = (User) session.getAttribute("user");
        // Get Luot xem trong ngày
        getIsIncreaseView(model, teacher);

        // Get revenue in month
        getStatiticRevenue(model);
        // end get statitics revenue calculation

        // Get total of sales in year
        getTotalSale(model, teacher);

        // TODO: Get 5 course bán chạy nhất
        getTop5CourseSale(model, teacher);
        // TODO: Get top cousrse đã bán

        // get 5 new feedback
        // lay list feedback according to teacher id
        getListFeedback(model, teacher);
        // end get 5 feedback
        return "teacher/index";
    }

    public void getIsIncreaseView(Model model, User teacher) {
        // LÁY LƯỢT XEM CỦA NGÀY HÔM NAY
        Long viewDay = viewService.getViewNumberByTeacherIdInDay(teacher.getId());
        // get view in yesterday
        Long viewYesterday = viewService.getViewNumberByTeacherIdInYesterday(teacher.getId());
        // Calculate
        Long view = viewYesterday - viewDay;
        boolean isIncreaseView = false;
        if (view < 0) {
            isIncreaseView = true;
            view = view * -1;
        }
        // Set model
        model.addAttribute("isIncreaseView", isIncreaseView);
        model.addAttribute("view", view);
    }

    public void getStatiticRevenue(Model model) {
        BigDecimal revenuNow = withdrawalDetailService.getRevenueByMonth();
        BigDecimal revenueBefore = withdrawalDetailService.getRevenueByMonthBefore();
        BigDecimal revenue = revenueBefore.subtract(revenuNow);
        boolean isIncreaseRevenue = false;
        if (revenue.compareTo(BigDecimal.ZERO) < 0) {
            isIncreaseRevenue = true;
            revenue = revenue.multiply(new BigDecimal(-1));
        }
        model.addAttribute("isIncreaseRevenue", isIncreaseRevenue);
        model.addAttribute("revenue", revenue);
    }

    public void getListFeedback(Model model, User teacher) {
        List<Feedback> listFeedback = feedbackServive.getFeedbackByTeacherId(teacher.getId());

        // lay 5 feedback gan nhat
        if (listFeedback.size() > 5) {
            int lastIndex = listFeedback.size() - 1;
            int firstIndex = lastIndex - 4;
            listFeedback = listFeedback.subList(firstIndex, lastIndex + 1);
        }
        model.addAttribute("isFeedback", listFeedback.size() != 0);
        model.addAttribute("feedbacks", listFeedback);
    }

    public void getTotalSale(Model model, User teacher) {
        int sum = 0;
        List<Course> list = courseService.getCourseByTeacherId(teacher.getId());
        for (Course course : list) {
            sum = sum + course.getOrderDetails().size();
        }
        model.addAttribute("totalOfSales", sum);
    }

    public void getTop5CourseSale(Model model, User teacher) {
        List<CourseDTOHomePage> list = new ArrayList<>();
        List<Course> listCourses = courseService.getCourseByTeacherId(teacher.getId());
        for (Course course : listCourses) {
            if (course.getOrderDetails().size() == 0) {
                continue;
            }
            CourseDTOHomePage courseDTOHomePage = modelMapper.map(course, CourseDTOHomePage.class);
            courseDTOHomePage.setTotalSale(course.getOrderDetails().size());
            courseDTOHomePage.setTotalMoney(
                    courseDTOHomePage.getPrice().multiply(new BigDecimal(courseDTOHomePage.getTotalSale())));
            list.add(courseDTOHomePage);
        }
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getTotalSale() < list.get(j).getTotalSale()) {
                    // Hoán đổi vị trí của hai phần tử để đưa phần tử lớn hơn lên trước
                    CourseDTOHomePage temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        // Lấy 5 phần tử đầu tiên của danh sách
        List<CourseDTOHomePage> listTopSale = list.subList(0, Math.min(list.size(), 5));
        model.addAttribute("listTopSale", listTopSale);
    }
}
