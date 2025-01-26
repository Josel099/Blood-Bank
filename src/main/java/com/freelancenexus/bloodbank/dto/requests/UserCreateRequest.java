package com.freelancenexus.bloodbank.dto.requests;

import com.freelancenexus.bloodbank.enums.BloodGroup;
import com.freelancenexus.bloodbank.enums.Roles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    private String userName;

    private String password;

    private Integer age;

    private String contactNum;

    private String address;

    private BloodGroup bloodGroup;

    private Roles role;

}
