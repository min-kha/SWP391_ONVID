package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;

public interface IUserService {
    boolean loginStudent(UserDTOLoginRequest student);

    public Collection<UserDTOAccountRequest> getAllUserDTOAccountRequest();

    public User getUserById(int id);

    public void changeStatus(int id);

    private List<User> getAllUsers();
}
