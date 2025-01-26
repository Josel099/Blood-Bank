package com.freelancenexus.bloodbank.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;
import com.freelancenexus.bloodbank.dto.requests.UserUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    User toUserEntity(UserCreateRequest userCreateRequest);

    UserResponseDTO toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequestDTO userUpdateRequestDTO, @MappingTarget User user);

    
}
