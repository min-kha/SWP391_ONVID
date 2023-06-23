package group5.swp391.onlinelearning.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.user.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.repository.UserRepository;
import group5.swp391.onlinelearning.service.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

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
