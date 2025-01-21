package com.freelancenexus.bloodbank.dto.responses;

import com.freelancenexus.bloodbank.enums.BloodGroup;
import com.freelancenexus.bloodbank.enums.Roles;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String userName;

    private Integer age;

    private String contactNum;

    private String address;

    private BloodGroup bloodGroup;

    private Roles role;

}
