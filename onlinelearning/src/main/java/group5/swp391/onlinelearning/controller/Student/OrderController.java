package group5.swp391.onlinelearning.controller.Student;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.IOrderService;

@Controller
@RequestMapping("/student")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/order")
    public String createOrder(HttpSession session, HttpServletRequest request, Model model) {
        BigDecimal amount = new BigDecimal(request.getParameter("total"));
        User student = (User) session.getAttribute("studentSession");
        MyOrder order = orderService.createOrder(amount, student);
        request.setAttribute("order", order);
        return "forward:/student/order-detail";
    }
}
