package com.freelancenexus.bloodbank.service.impl;

import org.springframework.stereotype.Service;

import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.db.repositories.UserRepository;
import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
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
    public void register(UserCreateRequest userCreateRequest) {
        User user = userMapper.toUserEntity(userCreateRequest);
        userRepository.save(user);
    }

    @Override
    public User findByusername(String userName) {
        // TODO Auto-generated method stub
        return null;
    }

}
