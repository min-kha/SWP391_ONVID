package group5.swp391.onlinelearning.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;

@Service
public interface IWithdrawalDetailService {
    public void addWithdrawalDetail(List<Course> courses, User student);

    public void updateWithdrawalDetail(WithdrawalDetail withdrawalDetail) throws Exception;

    public List<WithdrawalDetail> getListByTeacherId(int teacher);

    public List<WithdrawalDetail> getAllWithdrawalDetails();

    public List<WithdrawalDetail> getWithdrawalDetailsToReview();

    public void removeWithdrawalDetailByWallet(BigDecimal money);

    public BigDecimal getRevenueByMonth();

    public BigDecimal getRevenueByMonthBefore();

    public WithdrawalDetail getWithdrawalDetailById(int id);
}