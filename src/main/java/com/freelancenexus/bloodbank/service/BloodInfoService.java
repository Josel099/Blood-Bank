package com.freelancenexus.bloodbank.service;

import java.util.List;

import com.freelancenexus.bloodbank.dto.responses.BloodInfoResponse;

public interface BloodInfoService {

    List<BloodInfoResponse> getAllBloodInfo();
    
}
