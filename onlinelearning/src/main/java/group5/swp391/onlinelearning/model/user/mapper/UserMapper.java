package group5.swp391.onlinelearning.model.user.mapper;

import org.springframework.stereotype.Component;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.model.user.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTORegisterRequest;

@Component
public class UserMapper {
    public UserDTOAccountRequest mapperUsertoUserDTOAccountRequest(User user) {
        UserDTOAccountRequest userDTOAccountRequest = UserDTOAccountRequest.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
        userDTOAccountRequest.setRole(convertRole(user.getRole()));
        return userDTOAccountRequest;
    }

    private String convertRole(int role) {
        switch (role) {
            case 0:
                return "Student";
            case 1:
                return "Teacher";
            case 2:
                return "Staff";
            default:
                return "Admin";
        }
    }

    public User staffDTOCreateToUser(StaffDTOCreate staffDTOCreate) {
        User user = User.builder()
                .name(staffDTOCreate.getName())
                .email(staffDTOCreate.getEmail())
                .password(staffDTOCreate.getPassword())
                .role(2)
                .status(1)
                .build();
        return user;
    }

    public User userDTORegisterRequestToUser(UserDTORegisterRequest userDTORegisterRequest) {
        User user = User.builder()
                .name(userDTORegisterRequest.getName())
                .email(userDTORegisterRequest.getEmail())
                .password(userDTORegisterRequest.getPassword())
                .role(0)
                .status(1)
                .build();
        return user;
    }
}
