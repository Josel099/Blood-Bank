package com.freelancenexus.bloodbank.service;

import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoDTO;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.BloodRequestInfoResponseDTO;

public interface BloodRequestService {

    public BloodRequestInfoResponseDTO createRequest(BloodRequestInfoDTO requestInfoDTO);

    public BloodRequestInfoResponseDTO updateRequest(Long id,
        BloodRequestInfoUpdateRequestDTO requestInfoDTO);

}
