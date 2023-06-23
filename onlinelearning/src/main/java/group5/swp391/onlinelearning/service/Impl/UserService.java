package group5.swp391.onlinelearning.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.repository.UserRepository;
import group5.swp391.onlinelearning.service.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean loginStudent(UserDTOLoginRequest student, BindingResult bindingResult) {
        List<User> users = getAllUser();
        boolean isValid = false;
        for (User user : users) {
            if (student.getEmail().equals(user.getEmail()) && student.getPassword().equals(user.getPassword())) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            bindingResult.rejectValue("password", "login.error", "Đăng nhập không thành công");
        }
        return isValid;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
