package group5.swp391.onlinelearning.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;
import group5.swp391.onlinelearning.repository.WithDrawalDetailRepository;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;

@Service
public class WithdrawalDetailService implements IWithdrawalDetailService {

    @Autowired
    WithDrawalDetailRepository withDrawalDetailRepository;

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
            withdrawalDetail.setDescripton("Register course" + course.getName());
            withDrawalDetailRepository.save(withdrawalDetail);
        }
    }

    @Override
    // lấy hết tất cả các withdraw của teacher
    public List<WithdrawalDetail> getListByTeacherId(int teacher_id) {
        List<WithdrawalDetail> list = withDrawalDetailRepository.findByTeacherId(teacher_id);
        return list;
    }

    @Override
    // thêm withdrawal khi teacher yêu cầu rút tiền
    public void removeWithdrawalDetailByWallet(BigDecimal money) {
        User user = (User) session.getAttribute("userSession");
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        // set thuoc tinh
        withdrawalDetail.setDate(date);
        withdrawalDetail.setStatus(0);
        withdrawalDetail.setTeacher(user);
        withdrawalDetail.setUser(null);
        withdrawalDetail.setMoney(money);
        withdrawalDetail.setDescripton("Withdraw money " + money);
        withDrawalDetailRepository.save(withdrawalDetail);
    }

}
