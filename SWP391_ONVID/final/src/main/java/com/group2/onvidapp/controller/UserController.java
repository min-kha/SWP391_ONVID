package com.group2.onvidapp.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.group2.onvidapp.model.user.dto.UserDTOLoginRequest;
import com.group2.onvidapp.model.user.dto.UserDTOReponse;
import com.group2.onvidapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService UserService;

    private final UserService userService;



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");

        return "aa";
    }
}
