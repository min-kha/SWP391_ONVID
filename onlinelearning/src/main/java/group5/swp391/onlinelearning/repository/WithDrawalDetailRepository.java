package group5.swp391.onlinelearning.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import group5.swp391.onlinelearning.entity.WithdrawalDetail;

@Repository
public interface WithDrawalDetailRepository extends JpaRepository<WithdrawalDetail, Integer> {
    @Query(value = "SELECT * FROM swp391_onvid.withdrawal_detail where teacher_id = ?1", nativeQuery = true)
    public List<WithdrawalDetail> findByTeacherId(int studentId);

    @Query(value = "SELECT * FROM swp391_onvid.withdrawal_detail where teacher_id = ?1 AND MONTH(date) = MONTH(CURDATE()) AND YEAR(date) = YEAR(CURDATE());", nativeQuery = true)
    public List<WithdrawalDetail> getRevenueByMonth(int studentId);

    @Query(value = "SELECT * FROM swp391_onvid.withdrawal_detail where teacher_id = ?1 AND date >= DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 1 MONTH) AND date <= LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH));", nativeQuery = true)
    public List<WithdrawalDetail> getRevenueByMonthBefore(int studentId);

}
