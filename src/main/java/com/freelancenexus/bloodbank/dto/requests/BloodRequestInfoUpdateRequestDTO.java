package com.freelancenexus.bloodbank.dto.requests;

import com.freelancenexus.bloodbank.enums.RequestStatus;
import com.freelancenexus.bloodbank.enums.Urgency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BloodRequestInfoUpdateRequestDTO {

    private RequestStatus status;

    private Integer quantity;

    private Urgency urgency;

}