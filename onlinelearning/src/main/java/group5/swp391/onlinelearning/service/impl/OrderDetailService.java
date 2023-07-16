package group5.swp391.onlinelearning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.entity.OrderDetail;
import group5.swp391.onlinelearning.service.IOrderDetailService;
import group5.swp391.onlinelearning.repository.OrderDetailRepository;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public void createOrderDetail(MyOrder order, List<Course> courses) {
        for (Course c : courses) {
            OrderDetail orderDetail = OrderDetail.builder().course(c).order(order).build();
            orderDetailRepository.save(orderDetail);
        }
    }

    public List<OrderDetail> getMyCourses(int orderId) {
        return getOrderDetailsByOrderId(orderId);
    }

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        return (List<OrderDetail>) orderDetailRepository.getOrderDetailsByOrderId(orderId);
    }
}
