package group5.swp391.onlinelearning.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.WithdrawalDetail;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.WithDrawalDetailRepository;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;

@Service
public class WithdrawalDetailService implements IWithdrawalDetailService {

    @Autowired
    WithDrawalDetailRepository withdrawalDetailRepository;

    private HttpSession session;

    public WithdrawalDetailService(HttpSession session) {
        this.session = session;
    }

    @Override
    // thêm mới withdraw khi student thanh toan khóa học
    public void addWithdrawalDetail(List<Course> courses, User student) {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        for (Course course : courses) {
            WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
            // tinh tien
            BigDecimal percent = new BigDecimal("0.6");
            BigDecimal money = course.getPrice().multiply(percent);
            // set thuoc tinh
            withdrawalDetail.setDate(date);
            withdrawalDetail.setStatus(1);
            withdrawalDetail.setTeacher(course.getTeacher());
            withdrawalDetail.setUser(student);
            withdrawalDetail.setMoney(money);
            withdrawalDetail.setDescription("Register course" + course.getName());
            withdrawalDetailRepository.save(withdrawalDetail);
        }
    }

    @Override
    // lấy hết tất cả các withdraw của teacher
    public List<WithdrawalDetail> getListByTeacherId(int teacher_id) {
        List<WithdrawalDetail> list = withdrawalDetailRepository.findByTeacherId(teacher_id);
        return list;
    }

    @Override
    // thêm withdrawal khi teacher yêu cầu rút tiền
    public void removeWithdrawalDetailByWallet(BigDecimal money) {
        User user = (User) session.getAttribute("user");
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        // set thuoc tinh
        withdrawalDetail.setDate(date);
        withdrawalDetail.setStatus(1);
        withdrawalDetail.setTeacher(user);
        withdrawalDetail.setUser(null);
        // BigDecimal currentMoney = withdrawalDetail.getMoney();
        // currentMoney = currentMoney.subtract(money);
        withdrawalDetail.setMoney(money);
        withdrawalDetail.setDescription("Withdraw money " + money);
        withdrawalDetailRepository.save(withdrawalDetail);
    }

    @Override
    public BigDecimal getRevenueByMonth() {
        User user = (User) session.getAttribute("user");
        List<WithdrawalDetail> list = withdrawalDetailRepository.getRevenueByMonth(user.getId());
        BigDecimal revenue = BigDecimal.ZERO;
        for (WithdrawalDetail withdrawalDetail : list) {
            if (withdrawalDetail.getUser() != null) {
                revenue = revenue.add(withdrawalDetail.getMoney());
                if (withdrawalDetail.getUser().getRole() != 0) {
                    revenue = revenue.subtract(withdrawalDetail.getMoney());
                    list.remove(withdrawalDetail);
                }
            }
        }
        return revenue;
    }

    @Override
    public BigDecimal getRevenueByMonthBefore() {
        throw new UnsupportedOperationException("Unimplemented method 'getRevenueByMonthBefore'");
    }

    @Override
    public List<WithdrawalDetail> getAllWithdrawalDetails() {
        return withdrawalDetailRepository.findAll();
    }

    @Override
    public List<WithdrawalDetail> getWithdrawalDetailsToReview() {
        return withdrawalDetailRepository.findWithdrawalDetailsToReview();
    }

    @Override
    public WithdrawalDetail getWithdrawalDetailById(int id) {
        return withdrawalDetailRepository.findById(id).get();
    }

    @Override
    public void updateWithdrawalDetail(@NotNull WithdrawalDetail withdrawalDetail) throws Exception {
        Optional<WithdrawalDetail> withdrawalDetailTmp = withdrawalDetailRepository.findById(withdrawalDetail.getId());
        if (withdrawalDetailTmp.isPresent()) {
            withdrawalDetailRepository.save(withdrawalDetail);
        } else {
            throw new InvalidInputException("id", "withdrawalDetail.notfound", "WithdrawalDetail not found");
        }
    }
}
