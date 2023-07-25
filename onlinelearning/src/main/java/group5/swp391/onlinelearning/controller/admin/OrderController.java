package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.service.IOrderService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller("OrderAdminController")
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    IOrderService orderService;
    @Autowired
    ThymeleafBaseCRUD thymeleafBaseCRUD;

    @GetMapping("/index")
    public String getIndex(Model model, HttpSession session) {
        List<MyOrder> myOrders = orderService.getAllOrders();
        String title = "List Orders - Admin";
        thymeleafBaseCRUD.setBaseForList(model, myOrders, title);
        return "admin/order/index";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        try {
            MyOrder order = orderService.getOrderById(id);
            thymeleafBaseCRUD.setBaseForEntity(model, order, "Detail order - Admin");
        } catch (Exception e) {
            return "/error";
        }
        return "admin/order/detail";
    }
}
