package com.freelancenexus.bloodbank.enums;

import lombok.Getter;

@Getter
public enum Roles {

    ADMIN("ADMIN"),DONOR("DONOR"),RECEIVER("RECEIVER"),HOSPITAL_STAFF("HOSPITAL_STAFF");

    private final String role;
    
    Roles(String role){
        this.role=role;
    }
}
