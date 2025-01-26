package com.freelancenexus.bloodbank.mappers;

import org.mapstruct.Mapper;

import com.freelancenexus.bloodbank.db.entities.Notification;
import com.freelancenexus.bloodbank.dto.responses.NotificationResponseDTO;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponseDTO toResponseDTO(Notification notification);

}
