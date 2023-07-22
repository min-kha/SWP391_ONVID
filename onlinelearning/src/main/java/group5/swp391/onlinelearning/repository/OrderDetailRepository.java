package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query(value = "SELECT * FROM order_detail o WHERE o.order_id = ?1 ", nativeQuery = true)
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId);
}
