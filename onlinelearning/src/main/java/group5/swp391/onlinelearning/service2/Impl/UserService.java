package group5.swp391.onlinelearning.service2.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.StaffDTOCreate;
import group5.swp391.onlinelearning.model.user.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.user.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.model.user.mapper.UserMapper;
import group5.swp391.onlinelearning.repository2.UserRepository;
import group5.swp391.onlinelearning.service2.IUserService;

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

    public void changeStatus(int id) {
        User user = userRepository.findById(id).get();
        if (user.getStatus() == 1) {
            user.setStatus(0);
        } else {
            user.setStatus(1);
        }
        userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean loginStudent(UserDTOLoginRequest student) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (student.getEmail().equals(user.getEmail()) && student.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return false;
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
    public void addUserRegister(UserDTORegisterRequest userDTORegisterRequest) {
        User user = mapper.userDTORegisterRequestToUser(userDTORegisterRequest);
        userRepository.save(user);
    }

}