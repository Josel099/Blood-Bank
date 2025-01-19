package com.freelancenexus.bloodbank.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.freelancenexus.bloodbank.db.entities.BloodRequestInfo;
import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoDTO;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.BloodRequestInfoResponseDTO;

@Mapper(componentModel = "spring")
public interface BloodRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true), @Mapping(target = "user", source = "user"),
        @Mapping(target = "status", ignore = true),
        @Mapping(target = "requestedTime", ignore = true),
        @Mapping(target = "approvedTime", ignore = true)
    })
    BloodRequestInfo toBloodRequestInfoEntity(BloodRequestInfoDTO bloodRequestInfoDTO, User user);

    @Mappings({
        @Mapping(target = "userId", source = "bloodRequestInfo.user.id"),
        @Mapping(target = "userName", source = "bloodRequestInfo.user.userName"),
        @Mapping(target = "bloodGroup", source = "bloodRequestInfo.user.bloodGroup"),
    })
    BloodRequestInfoResponseDTO toResponseDTO(BloodRequestInfo bloodRequestInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBloodRequestFromDTO(BloodRequestInfoUpdateRequestDTO requestDTO,
        @MappingTarget BloodRequestInfo bloodRequestInfo);

}
