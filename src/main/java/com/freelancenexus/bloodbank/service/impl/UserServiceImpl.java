package com.freelancenexus.bloodbank.service.impl;

import org.springframework.stereotype.Service;

import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.db.repositories.UserRepository;
import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.requests.UserLoginDTO;
import com.freelancenexus.bloodbank.dto.responses.UserResponseDTO;
import com.freelancenexus.bloodbank.mappers.UserMapper;
import com.freelancenexus.bloodbank.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO register(UserCreateRequest userCreateRequest) {
        User user = userMapper.toUserEntity(userCreateRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDTO loginUser(UserLoginDTO loginInfo) {
        User user = userRepository.findByUserName(loginInfo.getUserName())
            .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(loginInfo.getPassword())) {
            throw new RuntimeException("Login Failed");
        }

        return userMapper.toResponse(user);
    }

    /**
     * get user info by Id
     */
    @Override
    public UserResponseDTO getUserInfo(Long id) {
        User user = userRepository.findById(id).get();

        return userMapper.toResponse(user);
    }

}
