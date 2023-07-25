package group5.swp391.onlinelearning.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.service.ICVService;
import group5.swp391.onlinelearning.service.IOrderDetailService;
import group5.swp391.onlinelearning.service.IOrderService;
import group5.swp391.onlinelearning.service.ITopicService;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;
import group5.swp391.onlinelearning.service.impl.CourseService;

@Controller(value = "HomeAdminController")
@RequestMapping("/admin")
public class HomeController {
    @Autowired
    private IUserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ICVService cvService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private IWithdrawalDetailService withdrawalDetailService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService orderDetailService;

    @GetMapping("")
    public String getDefault(Model model, RedirectAttributes redirectAttributes) {
        return "redirect:/admin/home";

    }

    @GetMapping("/home")
    public String getHome(Model model, RedirectAttributes redirectAttributes) {
        if (model.getAttribute("user") == null) {
            model.addAttribute("user", new UserDTOLoginRequest());
        }
        // get total
        model.addAttribute("numberOfUsers", userService.getAllUsers().size());
        model.addAttribute("numberOfCourses", courseService.getAllCourses().size());
        model.addAttribute("numberOfCourseReviews", courseService.getReviewCourses().size());
        model.addAttribute("numberOfCVs", cvService.getCVs().size());
        model.addAttribute("numberOfTopics", topicService.getTopics().size());
        model.addAttribute("numberOfWithdrawalDetails", withdrawalDetailService.getAllWithdrawalDetails().size());
        model.addAttribute("numberOfOrders", orderService.getAllOrders().size());
        model.addAttribute("numberOfOrderDetails", orderDetailService.getAllOrderDetails().size());
        return "/admin/home";
    }
}
