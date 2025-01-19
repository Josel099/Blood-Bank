package com.freelancenexus.bloodbank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.responses.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    User toUserEntity(UserCreateRequest userCreateRequest);

    UserResponse toResponse(User user);

}
