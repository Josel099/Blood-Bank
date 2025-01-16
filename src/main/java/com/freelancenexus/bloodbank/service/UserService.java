package com.freelancenexus.bloodbank.service;

import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;

public interface UserService {

    public void register(UserCreateRequest userCreateRequest);
    
    public User findByusername(String userName);
}
