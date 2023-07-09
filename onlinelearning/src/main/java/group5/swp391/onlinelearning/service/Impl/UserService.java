package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.model.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.model.mapper.UserMapper;
import group5.swp391.onlinelearning.repository.UserRepository;
import group5.swp391.onlinelearning.service.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper mapper;

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
        return userRepository.findById(id).get();
    }

    @Override
    public User loginStudent(UserDTOLoginRequest student, Model model) {
        boolean checkInvalidAccount = false;
        boolean checkWrongAccountOrPassword = false;
        List<User> users = getAllUsers();
        // TODO: CHECK BẰNG SQL BY MINH KHA
        for (User user : users) {
            if (student.getEmail().equals(user.getEmail()) && student.getPassword().equals(user.getPassword())) {
                if (user.getStatus()) {
                    return user;
                } else {
                    checkWrongAccountOrPassword = false;
                    checkInvalidAccount = true;
                    break;
                }
            } else {
                checkWrongAccountOrPassword = true;
            }
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
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUserRegister(UserDTORegisterRequest userDTORegisterRequest) {
        User user = mapper.userDTORegisterRequestToUser(userDTORegisterRequest);
        return userRepository.save(user);
    }
}