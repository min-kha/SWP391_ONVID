package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller("withdrawalDetailAdminController")
@RequestMapping("/admin/withdrawalDetails")
public class WithdrawalDetailController {

    @Autowired
    private IWithdrawalDetailService withdrawalDetailService;
    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/index")
    public String getIndex(Model model, HttpSession session) {
        List<WithdrawalDetail> withdrawalDetails = withdrawalDetailService.getWithdrawalDetailsToReview();
        String title = "List Withdrawals - Admin";
        model.addAttribute("session", session);
        thymeleafBaseCRUD.setBaseForList(model, withdrawalDetails, title);
        return "admin/withdrawalDetail/index";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        WithdrawalDetail withdrawalDetail = withdrawalDetailService.getWithdrawalDetailById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, withdrawalDetail, "Detail Withdrawal - Admin");
        return "admin/withdrawalDetail/detail";
    }

    @GetMapping("/review/{id}")
    public String getReview(Model model, @PathVariable @NotNull int id, HttpSession session) throws Exception {
        try {
            WithdrawalDetail withdrawalDetail = withdrawalDetailService.getWithdrawalDetailById(id);
            User user = (User) session.getAttribute("user");
            if (withdrawalDetail.getStatus() == 0) {
                withdrawalDetail.setUser(user);
                withdrawalDetail.setStatus(1); // set status to In progress
                withdrawalDetailService.updateWithdrawalDetail(withdrawalDetail);
            }
            thymeleafBaseCRUD.setBaseForEntity(model, withdrawalDetail, "Review Withdrawal - Admin");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "admin/withdrawalDetail/review";
    }

    @PostMapping("/review/{id}")
    public String postReview(Model model, @PathVariable @NotNull int id, @RequestParam String approve) {
        WithdrawalDetail withdrawalDetail = withdrawalDetailService.getWithdrawalDetailById(id);
        try {
            if (approve.equals("true")) {
                withdrawalDetail.setStatus(2); // set status to approved
                withdrawalDetailService.updateWithdrawalDetail(withdrawalDetail);

            }
            if (approve.equals("false")) {
                withdrawalDetail.setStatus(3); // set status to rejected
                withdrawalDetailService.updateWithdrawalDetail(withdrawalDetail);
            }
            thymeleafBaseCRUD.setBaseForEntity(model, withdrawalDetail, "Detail Withdrawal - Admin");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "redirect:/admin/withdrawalDetails/detail/{id}";
    }
}
