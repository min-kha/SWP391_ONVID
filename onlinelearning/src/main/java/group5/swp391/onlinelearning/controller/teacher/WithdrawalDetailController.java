package group5.swp391.onlinelearning.controller.teacher;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;
import group5.swp391.onlinelearning.service.IWalletService;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;

@Controller
@RequestMapping("/teacher/wallet/")
public class WithdrawalDetailController {
    @Autowired
    IWithdrawalDetailService withDetailService;

    @Autowired
    IWalletService walletService;

    @GetMapping("list")
    public String getListWallet(Model model, HttpSession session) {
        User teacher = (User) session.getAttribute("user");
        List<WithdrawalDetail> lists = withDetailService.getListByTeacherId(teacher.getId());
        String title = "List Wallet";
        model.addAttribute("title", title);
        model.addAttribute("wallets", lists);
        return "teacher/withDrawalDetail/list";
    }

    @GetMapping("create")
    public String getCreateWallet(Model model) {
        model.addAttribute("title", "Create Request Wallet");
        model.addAttribute("error", false);
        return "teacher/withDrawalDetail/create";
    }

    @PostMapping("create")
    public String postCreateWallet(HttpServletRequest req, Model model, HttpSession session) {
        Boolean error = false;
        String errorName = "";
        // get money and check valid
        BigDecimal money = null;
        WithdrawalDetail withdrawalDetail = null;
        try {
            BigDecimal wallet = walletService.getRevenue();
            if (req.getParameter("money") == null) {
                errorName = "Input is notValid";
                throw new Exception();
            }

            money = new BigDecimal(req.getParameter("money"));
            if (money.compareTo(BigDecimal.ZERO) < 0) {
                errorName = "Input is not < 0";
                throw new Exception();
                // get wallet and compare with money
            } else if (money.compareTo(wallet) > 0) {
                errorName = "Input is not > wallet";
                throw new Exception();
                // create withdrawal detail and change wallet
            } else {
                withdrawalDetail = new WithdrawalDetail();
                // add withdrawalDetail
                withDetailService.removeWithdrawalDetailByWallet(money);
                // update wallet
                walletService.subRevenue(money);
            }

        } catch (Exception e) {
            error = true;
            model.addAttribute("title", "Create Request Wallet");
            model.addAttribute("error", true);
            model.addAttribute("errorName", errorName);
            return "teacher/withDrawalDetail/create";
        }

        return "redirect:/teacher/wallet/list";
    }
}