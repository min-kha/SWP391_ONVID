package group5.swp391.onlinelearning.controller.teacher;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.IViewService;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;

@Controller(value = "TeacherController")
@RequestMapping("/teacher/")
public class HomeController {
    @Autowired
    IWithdrawalDetailService withdrawalDetailService;

    @Autowired
    IViewService viewService;

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

        // TODO: get 5 review moi nhat

        return "teacher/index";
    }
}
