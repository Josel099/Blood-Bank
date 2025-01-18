package com.freelancenexus.bloodbank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.dto.requests.UserCreateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "age", ignore = true),
        @Mapping(target = "contactNum", ignore = true), 
        @Mapping(target = "address", ignore = true),
        @Mapping(target = "bloodGroup", ignore = true),
    })
    User toUserEntity(UserCreateRequest userCreateRequest);

}
