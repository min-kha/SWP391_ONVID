package group5.swp391.onlinelearning.service;

import java.util.Collection;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.model.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;

public interface IUserService {
    public Collection<User> getAllUsers();

    public Collection<UserDTOAccountRequest> getAllUserDTOAccountRequest();

    public User getUserById(int id);

    public void changeStatus(int id);

    public void addStaff(StaffDTOCreate staffDTOCreate);

    public User getUserByEmail(String email);

    public void addUserRegister(UserDTORegisterRequest userDTORegisterRequest);

    public User loginStudent(UserDTOLoginRequest student, Model model);

}
