package com.group2.onvidapp.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group2.onvidapp.entity.User;
import com.group2.onvidapp.model.user.dto.UserDTOLoginRequest;
import com.group2.onvidapp.model.user.dto.UserDTOReponse;
import com.group2.onvidapp.repository.UserReposioty;
import com.group2.onvidapp.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserReposioty userReposioty;

    @Override
    public Map<String, UserDTOReponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap) {
        UserDTOLoginRequest userDTOLoginRequest = userLoginRequestMap.get("user");
        Optional<User> userOptional = userReposioty.findbyEmail(userDTOLoginRequest.getEmail());
        return null;
    }

}
