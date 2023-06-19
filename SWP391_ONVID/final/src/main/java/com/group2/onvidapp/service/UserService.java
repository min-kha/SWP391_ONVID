package com.group2.onvidapp.service;

import java.util.Map;

import com.group2.onvidapp.model.user.dto.UserDTOLoginRequest;
import com.group2.onvidapp.model.user.dto.UserDTOReponse;

public interface UserService {

    public Map<String, UserDTOReponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap);

}
