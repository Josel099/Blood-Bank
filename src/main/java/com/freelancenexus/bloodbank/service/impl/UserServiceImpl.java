package com.freelancenexus.bloodbank.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.db.repositories.BloodRequestRepository;
import com.freelancenexus.bloodbank.db.repositories.UserRepository;
import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.requests.UserLoginDTO;
import com.freelancenexus.bloodbank.dto.requests.UserUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.UserResponseDTO;
import com.freelancenexus.bloodbank.enums.Roles;
import com.freelancenexus.bloodbank.mappers.UserMapper;
import com.freelancenexus.bloodbank.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BloodRequestRepository bloodRequestRepository;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
        BloodRequestRepository bloodRequestRepository) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bloodRequestRepository = bloodRequestRepository;
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

    @Override
    public UserResponseDTO updateUser(UserUpdateRequestDTO updateRequestDTO) {

        User user = userRepository.findById(updateRequestDTO.getId()).get();

        userMapper.updateUserFromDto(updateRequestDTO, user);
        ;

        return userMapper.toResponse(userRepository.save(user));

    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {

        bloodRequestRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    /**
     * Retrieves a paginated list of users, excluding users with the ADMIN role.
     *
     * @param page the page number to retrieve (0-based indexing).
     * @param size the number of records per page.
     * @param sort the sorting criteria (e.g., "fieldName,asc" or "fieldName,desc").
     * @return a paginated {@link Page} of {@link UserResponseDTO}, containing only non-admin users.
     *         The results are sorted and paginated based on the provided parameters.
     * 
     * @throws IllegalArgumentException if the page or size parameters are invalid.
     */
    @Override
    public Page<UserResponseDTO> getAllUsers(int page, int size, String sort) {
        Pageable pageable = constructPageable(page, size, sort);
        Page<User> requests = userRepository.findAll(pageable);
        // Filter out users with ADMIN role and map to DTO
        List<UserResponseDTO> filteredUsers = requests.getContent().stream()
            .filter(user -> user.getRole() != Roles.ADMIN).map(userMapper::toResponse).toList();

        return new PageImpl<>(filteredUsers, pageable, filteredUsers.size());
    }

    /**
     * Constructs a Pageable object based on page number, size, and sorting criteria.
     *
     * @param page Page number to retrieve (0-based).
     * @param size Number of items per page.
     * @param sort Sorting criteria in the format "field,direction" (e.g., "id,asc").
     * @return A Pageable object configured with the specified parameters.
     **/
    private Pageable constructPageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort sorting = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        return PageRequest.of(page, size, sorting);
    }

}
