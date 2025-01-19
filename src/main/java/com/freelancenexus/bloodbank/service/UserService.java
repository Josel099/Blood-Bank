package com.freelancenexus.bloodbank.service;

import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.requests.UserLoginDTO;
import com.freelancenexus.bloodbank.dto.responses.UserResponse;

public interface UserService {

    public UserResponse register(UserCreateRequest userCreateRequest);
    
    public UserResponse loginUser(UserLoginDTO loginInfo);
}
