package com.freelancenexus.bloodbank.dto.responses;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponseDTO {

    private Long id;

    private String message;

    private ZonedDateTime createdAt;

    private Boolean markAsRead;

}
