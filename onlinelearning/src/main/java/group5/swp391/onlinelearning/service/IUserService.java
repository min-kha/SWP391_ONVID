package group5.swp391.onlinelearning.service;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.ui.Model;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.admin.UserDto;
import group5.swp391.onlinelearning.model.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.model.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;

public interface IUserService {
    public List<User> getAllUsers();

    public User addTeacherRegister(UserDTORegisterRequest userDTORegisterRequest);

    public Collection<UserDTOAccountRequest> getAllUserDTOAccountRequest();

    public User getUserById(int id);

    public void changeStatus(int id) throws Exception;

    public void addStaff(StaffDTOCreate staffDTOCreate);

    public User getUserByEmail(String email);

    public User addUserRegister(UserDTORegisterRequest userDTORegisterRequest);

    public User loginStudent(UserDTOLoginRequest student, Model model);

    public User loginTeacher(UserDTOLoginRequest student, Model model, Boolean isProcess);

    public User changePassword(User user);

    public void addStaff(User user) throws Exception;

    public void updateUser(User user) throws Exception;

    public void deleteUser(@NotNull int id);

    public UserDto map(User user);

    public int getRoleNumber(String role) throws Exception;

    public User map(UserDto user) throws Exception;

}
