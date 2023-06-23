package group5.swp391.onlinelearning.service2;

import java.util.Collection;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.model.user.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTORegisterRequest;

public interface IUserService {
    public Collection<User> getAllUsers();

    public Collection<UserDTOAccountRequest> getAllUserDTOAccountRequest();

    public User getUserById(int id);

    public void changeStatus(int id);

    public boolean loginStudent(UserDTOLoginRequest student);

    public void addStaff(StaffDTOCreate staffDTOCreate);

    public User getUserByEmail(String email);

    public void addUserRegister(UserDTORegisterRequest userDTORegisterRequest);
}
