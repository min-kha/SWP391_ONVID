package group5.swp391.onlinelearning.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.OrderRepository;
import group5.swp391.onlinelearning.service.IOrderService;

@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public MyOrder createOrder(BigDecimal amount, User student) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        MyOrder order = MyOrder.builder().student(student).amount(amount).date(currentDate).build();
        return orderRepository.save(order);
    }

    @Override
    public List<MyOrder> getOrderByStudentId(int studentId) {
        return orderRepository.getOrderByStudentId(studentId);
    }

    @Override
    public List<MyOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public MyOrder getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public void deleteMyOrderById(int orderId) {
        orderRepository.deleteById(orderId);
    }
}
