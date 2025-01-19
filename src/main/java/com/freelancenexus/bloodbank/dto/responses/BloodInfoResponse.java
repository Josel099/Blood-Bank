package com.freelancenexus.bloodbank.dto.responses;

import com.freelancenexus.bloodbank.enums.BloodGroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BloodInfoResponse {

    private Long Id;

    private BloodGroup bloodGroup;

    private Integer quantity;

}
