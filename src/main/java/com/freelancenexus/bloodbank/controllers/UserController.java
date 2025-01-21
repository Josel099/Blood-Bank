package com.freelancenexus.bloodbank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.requests.UserLoginDTO;
import com.freelancenexus.bloodbank.dto.requests.UserUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.UserResponseDTO;
import com.freelancenexus.bloodbank.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> addUser(
        @RequestBody UserCreateRequest userCreateRequest) {
        UserResponseDTO response = userService.register(userCreateRequest);
        log.info("User added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO loginInfo) {
        UserResponseDTO responseDTO = userService.loginUser(loginInfo);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> fetchUser(@PathVariable Long id) {
        UserResponseDTO response = userService.getUserInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUserInfo(
        @RequestBody UserUpdateRequestDTO updateRequestDTO) {
        UserResponseDTO response = userService.updateUser(updateRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
