package group5.swp391.onlinelearning.controller.teacher;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller
@RequestMapping("/teacher/wallet/")
public class WithdrawalDetailController{
    @Autowired
    IWithdrawalDetailService withDetailService;

    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;

    @GetMapping("list")
    public String getListWallet(Model model,HttpSession session){
        User teacher = (User) session.getAttribute("userSession");
        List<WithdrawalDetail> lists = withDetailService.getListByTeacherId(teacher.getId());
        String title = "List Wallet";
        thymeleafBaseCRUD.setBaseForList(model, lists, title);
        return "404";
    }
}