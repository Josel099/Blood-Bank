package com.freelancenexus.bloodbank.dto.requests;

import com.freelancenexus.bloodbank.enums.BloodGroup;
import com.freelancenexus.bloodbank.enums.Urgency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BloodRequestInfoDTO {

    private Long userId; // Reference to the user

    private Integer quantity;

    // this field is only for recivier not applicable for donors .
    private Urgency urgency;

    // this field only for hospital staff for requesting
    private BloodGroup bloodGroup;

}
