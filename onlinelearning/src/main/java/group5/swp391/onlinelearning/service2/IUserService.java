package group5.swp391.onlinelearning.service2;

import java.util.Collection;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;

public interface IUserService {
    public Collection<User> getAllUsers();

    public Collection<UserDTOAccountRequest> getAllUserDTOAccountRequest();

    public User getUserById(int id);

    public void changeStatus(int id);

    public User loginStudent(UserDTOLoginRequest student, Model model);
}
