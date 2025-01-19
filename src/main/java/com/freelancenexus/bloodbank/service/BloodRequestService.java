package com.freelancenexus.bloodbank.service;

import org.springframework.data.domain.Page;

import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoDTO;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.BloodRequestInfoResponseDTO;
import com.freelancenexus.bloodbank.enums.RequestStatus;

public interface BloodRequestService {

    public BloodRequestInfoResponseDTO createRequest(BloodRequestInfoDTO requestInfoDTO);

    public BloodRequestInfoResponseDTO updateRequest(Long id,
        BloodRequestInfoUpdateRequestDTO requestInfoDTO);

    public Page<BloodRequestInfoResponseDTO> getAllRequests(int page, int size, String sort);

    public Page<BloodRequestInfoResponseDTO> getRequestsByStatus(RequestStatus status, int page, int size, String sort);

    public Page<BloodRequestInfoResponseDTO> getRequestsByUserId(Long userId, int page, int size, String sort);

}
