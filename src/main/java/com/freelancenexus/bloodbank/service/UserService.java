package com.freelancenexus.bloodbank.service;

import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.requests.UserLoginDTO;
import com.freelancenexus.bloodbank.dto.requests.UserUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.UserResponseDTO;

public interface UserService {

    public UserResponseDTO register(UserCreateRequest userCreateRequest);
    
    public UserResponseDTO loginUser(UserLoginDTO loginInfo);

    public UserResponseDTO getUserInfo(Long id);

    public UserResponseDTO updateUser(UserUpdateRequestDTO updateRequestDTO);

    public void deleteUserById(Long id);
}
