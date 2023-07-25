package group5.swp391.onlinelearning.controller.teacher;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.IFeedbackServive;
import group5.swp391.onlinelearning.service.IViewService;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;

@Controller(value = "TeacherController")
@RequestMapping("/teacher/")
public class HomeController {
    @Autowired
    IWithdrawalDetailService withdrawalDetailService;

    @Autowired
    IViewService viewService;

    @Autowired
    IFeedbackServive feedbackServive;

    @GetMapping("home")
    public String getHomePage(Model model, HttpSession session) {
        // Get Luot xem trong ngày
        // LÁY LƯỢT XEM CỦA NGÀY HÔM NAY
        User teacher = (User) session.getAttribute("user");
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
        // End View

        // Get revenue in month
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
        // end get statitics revenue calculation

        // TODO: Get customer in year

        // TODO: Get 5 course vừa bán

        // TODO: Get top cousrse đã bán

        // get 5 new feedback
        // lay list feedback according to teacher id
        List<Feedback> listFeedback = feedbackServive.getFeedbackByTeacherId(teacher.getId());

        // lay 5 feedback gan nhat
        if (listFeedback.size() > 5) {
            int lastIndex = listFeedback.size() - 1;
            int firstIndex = lastIndex - 4;
            listFeedback = listFeedback.subList(firstIndex, lastIndex + 1);
        }
        model.addAttribute("isFeedback", listFeedback.size() != 0);
        model.addAttribute("feedbacks", listFeedback);
        // end get 5 feedback
        return "teacher/index";
    }
}
