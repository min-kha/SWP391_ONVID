package group5.swp391.onlinelearning.model.user.mapper;

import org.springframework.stereotype.Component;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOAccountRequest;

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
}
