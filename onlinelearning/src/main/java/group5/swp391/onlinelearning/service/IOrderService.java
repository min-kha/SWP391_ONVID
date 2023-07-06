package group5.swp391.onlinelearning.service;

import java.math.BigDecimal;
import java.util.List;

import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.entity.User;

public interface IOrderService {
    public MyOrder createOrder(BigDecimal amount, User student);

    public List<MyOrder> getOrderByStudentId(int studentId);
}
