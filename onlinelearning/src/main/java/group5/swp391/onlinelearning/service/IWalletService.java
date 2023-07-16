package group5.swp391.onlinelearning.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;

@Service
public interface IWalletService {
    public BigDecimal getRevenue();

    public void changeRevenue(List<Course> listCourse);
}