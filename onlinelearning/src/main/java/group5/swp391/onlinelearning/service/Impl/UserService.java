package group5.swp391.onlinelearning.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.repository.UserRepository;
import group5.swp391.onlinelearning.service.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean loginStudent(UserDTOLoginRequest student) {
        List<User> users = getAllUser();
        for (User user : users) {
            if (student.getEmail().equals(user.getEmail()) && student.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
