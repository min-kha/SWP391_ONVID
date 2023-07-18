package group5.swp391.onlinelearning.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;

@Service
public interface IWalletService {
    // get current money of the teacher (get by session)
    public BigDecimal getRevenue();

    // Add revenue when student payment
    public void changeRevenue(List<Course> listCourse);

    // Sub Revenue when teacher withdrawals
    public void subRevenue(BigDecimal money);

    // Create a new wallet when teacher register is successful
    public void createWallet(int teacherId);
}