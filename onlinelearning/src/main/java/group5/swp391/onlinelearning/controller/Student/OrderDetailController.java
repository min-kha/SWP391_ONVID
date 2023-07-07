package group5.swp391.onlinelearning.controller.student;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.entity.OrderDetail;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.IOrderService;
import group5.swp391.onlinelearning.service.Impl.OrderDetailService;

@Controller
@RequestMapping("/student")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    IOrderService orderService;

    @PostMapping("/order-detail")
    public String getOrderDetail(HttpSession session, HttpServletRequest request) {
        MyOrder order = (MyOrder) request.getAttribute("order");
        List<Course> courses = (List<Course>) session.getAttribute("cartStudentSession");
        orderDetailService.createOrderDetail(order, courses);
        return "forward:/student/cart/pay";
    }

}
