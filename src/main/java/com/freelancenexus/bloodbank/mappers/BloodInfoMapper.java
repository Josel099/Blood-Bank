package com.freelancenexus.bloodbank.mappers;

import org.mapstruct.Mapper;

import com.freelancenexus.bloodbank.db.entities.BloodInfo;
import com.freelancenexus.bloodbank.dto.responses.BloodInfoResponse;

@Mapper(componentModel = "spring")
public interface BloodInfoMapper {

    BloodInfoResponse toResponse(BloodInfo bloodInfo);

}
