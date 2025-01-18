package com.freelancenexus.bloodbank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.register(userCreateRequest);
        log.info("User added successfully");
        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

}
