package com.freelancenexus.bloodbank.dto.responses;

import java.time.ZonedDateTime;

import com.freelancenexus.bloodbank.enums.BloodGroup;
import com.freelancenexus.bloodbank.enums.RequestStatus;
import com.freelancenexus.bloodbank.enums.Urgency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BloodRequestInfoResponseDTO {

    private Long id;

    private Long userId;

    private String userName;

    private RequestStatus status;

    private BloodGroup bloodGroup;

    private Integer quantity;

    private ZonedDateTime requestedTime;

    private ZonedDateTime approvedTime;

    private ZonedDateTime transactionCompletedTime;

    private Urgency urgency;

}
