package group5.swp391.onlinelearning.controller.teacher;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.service.IWithdrawalDetailService;

@Controller(value = "TeacherController")
@RequestMapping("/teacher/")
public class HomeController {
    @Autowired
    IWithdrawalDetailService withdrawalDetailService;

    @GetMapping("home")
    public String getHomePage(Model model) {
        // TODO: Get Luot xem trong ngày

        // Get revenue in month
        BigDecimal revenue = withdrawalDetailService.getRevenueByMonth();
        BigDecimal revenueBefore = withdrawalDetailService.getRevenueByMonthBefore();
        BigDecimal sub = revenueBefore.subtract(revenue);
        boolean isIncrease = true;
        if (sub.compareTo(BigDecimal.ZERO) < 0) {
            isIncrease = false;
            sub = sub.multiply(new BigDecimal(-1));
        }
        if (revenueBefore.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal percentRevenue = sub.divide(revenueBefore).multiply(new BigDecimal(100));
            model.addAttribute("percentRevenue", percentRevenue);
        } else {
            model.addAttribute("percentRevenue", false);
        }
        model.addAttribute("isIncreaseRevenue", isIncrease);
        model.addAttribute("revenue", revenue);
        // Get customer in year

        // TODO: Get 5 course vừa bán
        // TODO: Get top cousrse đã bán
        // TODO: get 5 review moi nhat

        return "teacher/index";
    }
}
