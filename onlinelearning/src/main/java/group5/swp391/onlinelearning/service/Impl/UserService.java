package group5.swp391.onlinelearning.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.Repository.UserRepository;
import group5.swp391.onlinelearning.Service.IUserService;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOAccountRequest;
import group5.swp391.onlinelearning.model.user.mapper.UserMapper;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
            }

    @Override
    public List<UserDTOAccountRequest> getAllUserDTOAccountRequest() {
        // TODO Auto-generated method stub
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

}
