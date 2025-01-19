package com.freelancenexus.bloodbank.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BloodRequestInfoDTO {

    private Long userId; // Reference to the user
    
    private Integer quantity;

    
}
