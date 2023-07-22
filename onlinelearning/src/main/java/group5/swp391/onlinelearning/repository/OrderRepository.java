package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.MyOrder;

public interface OrderRepository extends JpaRepository<MyOrder, Integer> {
    @Query(value = "SELECT DISTINCT * FROM my_order m WHERE m.student_id = ?1 ", nativeQuery = true)
    public List<MyOrder> getOrderByStudentId(int studentId);

}
