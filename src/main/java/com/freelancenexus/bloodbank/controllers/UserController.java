package com.freelancenexus.bloodbank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.requests.UserLoginDTO;
import com.freelancenexus.bloodbank.dto.requests.UserUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.UserResponseDTO;
import com.freelancenexus.bloodbank.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
    @Operation(summary = "Register a new user", description = "Adds a new user to the system and returns their details.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration details", required = true, content = @Content(schema = @Schema(implementation = UserCreateRequest.class))), responses = {
        @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
    })
    public ResponseEntity<UserResponseDTO> addUser(
        @RequestBody UserCreateRequest userCreateRequest) {
        UserResponseDTO response = userService.register(userCreateRequest);
        log.info("User added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/login")
    @Operation(summary = "Login user", description = "Logs in a user and returns their details.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User login information", required = true, content = @Content(schema = @Schema(implementation = UserLoginDTO.class))), responses = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
    })
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO loginInfo) {
        UserResponseDTO responseDTO = userService.loginUser(loginInfo);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Fetch user by ID", description = "Retrieves user details by their ID.", parameters = @Parameter(name = "id", description = "User ID", required = true), responses = {
        @ApiResponse(responseCode = "200", description = "User details retrieved successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> fetchUser(@PathVariable Long id) {
        UserResponseDTO response = userService.getUserInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Operation(summary = "Update user information", description = "Updates details of an existing user.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user information", required = true, content = @Content(schema = @Schema(implementation = UserUpdateRequestDTO.class))), responses = {
        @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
    })
    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUserInfo(
        @RequestBody UserUpdateRequestDTO updateRequestDTO) {
        UserResponseDTO response = userService.updateUser(updateRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete user by ID", description = "Deletes a user from the system by their ID.", parameters = @Parameter(name = "id", description = "User ID", required = true), responses = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User with Id:" + id + " deleted Sucessfully", HttpStatus.OK);
    }

    /**
     *  Retrieves a paginated list of users, excluding users with the ADMIN role.
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @Operation(summary = "Get all users", description = "Retrieves a paginated list of users, excluding ADMIN roles.", parameters = {
        @Parameter(name = "page", description = "Page number for pagination", example = "0"),
        @Parameter(name = "size", description = "Number of users per page", example = "10"),
        @Parameter(name = "sort", description = "Sorting criteria (e.g., id,asc)", example = "id,asc")
    }, responses = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = @Content(schema = @Schema(implementation = Page.class)))
    })

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sort));
    }

}
