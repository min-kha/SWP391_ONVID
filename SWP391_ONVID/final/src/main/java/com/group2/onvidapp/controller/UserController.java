package com.group2.onvidapp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group2.onvidapp.model.user.dto.UserDTOLoginRequest;
import com.group2.onvidapp.model.user.dto.UserDTOReponse;
import com.group2.onvidapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/login")
    public Map<String, UserDTOReponse> login(@RequestBody Map<String, UserDTOLoginRequest> userLoginRequestMap) {
        return userService.authenticate(userLoginRequestMap);
    }

}
