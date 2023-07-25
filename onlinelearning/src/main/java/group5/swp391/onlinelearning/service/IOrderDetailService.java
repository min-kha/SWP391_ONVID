package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.entity.OrderDetail;

public interface IOrderDetailService {
    public void createOrderDetail(MyOrder order, List<Course> courses);

    public List<OrderDetail> getAllOrderDetails();

}
