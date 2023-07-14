package group5.swp391.onlinelearning.service;

import java.util.List;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;

@Service
public interface IWithdrawalDetailService {
    public void addWithdrawalDetail(List<Course> courses, User student);

    public List<WithdrawalDetail> getListByTeacherId(int teacher);
}