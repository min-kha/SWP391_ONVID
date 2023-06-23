package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;

public interface IUserService {
    boolean loginStudent(UserDTOLoginRequest student);
    List<User> getAllUsers();
}
