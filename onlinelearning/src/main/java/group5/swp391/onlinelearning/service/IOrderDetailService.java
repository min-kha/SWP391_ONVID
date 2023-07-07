package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.MyOrder;

public interface IOrderDetailService {
    public void createOrderDetail(MyOrder order, List<Course> courses);
}
