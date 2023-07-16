package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.model.admin.UserDto;
import group5.swp391.onlinelearning.model.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.model.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.model.mapper.UserMapper;
import group5.swp391.onlinelearning.repository.UserRepository;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.utils.SHA1;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper mapper;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDTOAccountRequest> getAllUserDTOAccountRequest() {
        List<User> list = this.getAllUsers();
        List<UserDTOAccountRequest> listUserDTOAccountRequest = new ArrayList<UserDTOAccountRequest>();
        for (User user : list) {
            listUserDTOAccountRequest.add(mapper.mapperUsertoUserDTOAccountRequest(user));
        }
        return listUserDTOAccountRequest;
    }

    // TODO: MUST CHANGE, After changes variable type of status from int to Boolean
    public void changeStatus(int id) {
        User user = userRepository.findById(id).get();
        if (user.getStatus()) {
            user.setStatus(false);
        } else {
            user.setStatus(true);
        }
        userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User loginStudent(UserDTOLoginRequest student, Model model) {
        boolean checkInvalidAccount = false;
        boolean checkWrongAccountOrPassword = false;
        User user = userRepository.login(student.getEmail());
       
        // TODO: CHECK BẰNG SQL BY MINH KHA
       
            if (student.getEmail().equals(user.getEmail())
                    && SHA1.toSHA1(student.getPassword()).equals(user.getPassword())) {
                if (user.getStatus()) {
                    return user;
                } else {
                    checkWrongAccountOrPassword = false;
                    checkInvalidAccount = true;
                }
            } else {
                checkWrongAccountOrPassword = true;
            }
        
        if (checkInvalidAccount) {
            model.addAttribute("invalidAccount", "Your account has been locked");
        }
        if (checkWrongAccountOrPassword) {
            model.addAttribute("wrongAccountOrPassword", "Email or password not true");
        }
        return null;
    }

    @Override
    public void addStaff(StaffDTOCreate staffDTOCreate) {
        User user = mapper.staffDTOCreateToUser(staffDTOCreate);
        // TODO: what is role of staff?
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUserRegister(UserDTORegisterRequest userDTORegisterRequest) {
        userDTORegisterRequest.setPassword(SHA1.toSHA1(userDTORegisterRequest.getPassword()));
        User user = mapper.userDTORegisterRequestToUser(userDTORegisterRequest);
        return userRepository.save(user);
    }

    @Override
    public User changePassword(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addStaff(@NotNull User user) throws Exception {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new InvalidInputException("", "user.duplicate", "User is already exists");
        }
        if (isDuplicateEmail(user)) {
            throw new InvalidInputException("email", "email.duplicate", "Duplicate user email");
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(@NotNull User user) throws Exception {
        Optional<User> userTmp = userRepository.findById(user.getId());
        if (userTmp.isPresent()) {
            // Duplicate and had changed
            if (isDuplicateEmail(user) && !user.getEmail().equals(userTmp.get().getEmail())) {
                throw new InvalidInputException("email", "email.duplicate", "Duplicate user email");
            }
            userRepository.save(user);
        } else {
            throw new InvalidInputException("id", "user.notfound", "User not found");
        }
    }

    @Override
    public void deleteUser(@NotNull int id) {
        userRepository.deleteById(id);
    }

    private boolean isDuplicateEmail(User user) {
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    public String getRoleName(int role) {
        switch (role) {
            case 0:
                return "STUDENT";
            case 1:
                return "TEACHER";
            case 2:
                return "STAFF";
            case 3:
                return "ADMIN";
            default:
                return "UNKNOWN";
        }
    }

    public int getRoleNumber(String role) throws Exception {
        switch (role) {
            case "STUDENT":
                return 0;
            case "TEACHER":
                return 1;
            case "STAFF":
                return 2;
            case "ADMIN":
                return 3;
            default:
                throw new Exception("Invalid string role");
        }
    }

    public UserDto map(User user) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRoleName(getRoleName(user.getRole()));
        return userDto;
    }

    public User map(UserDto userDto) throws Exception {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDto, User.class);
        try {
            String oldPassword = this.getUserById(userDto.getId()).getPassword();
            user.setPassword(oldPassword);
            user.setRole(getRoleNumber(userDto.getRoleName()));
        } catch (Exception e) {
            throw new InvalidInputException("roleName", "role.invalid", e.getMessage());
        }
        return user;
    }
}
