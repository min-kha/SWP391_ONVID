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

        // TODO: Get revenue in month
        BigDecimal revenue = withdrawalDetailService.getRevenueByMonth();
        model.addAttribute("revenue", revenue);
        // TODO: Get customer in year

        // TODO: Get 5 course vừa bán
        // TODO: Get top cousrse đã bán
        // TODO: get 5 review moi nhat

        return "teacher/index";
    }
}
