package com.freelancenexus.bloodbank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoDTO;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.BloodRequestInfoResponseDTO;
import com.freelancenexus.bloodbank.service.BloodRequestService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/blood_request")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;


    /**
     * @param bloodRequestService
     */
    public BloodRequestController(BloodRequestService bloodRequestService) {
        super();
        this.bloodRequestService = bloodRequestService;
    }

    @PostMapping
    public ResponseEntity<BloodRequestInfoResponseDTO> bloodRequestCreateRequest(
        @RequestBody BloodRequestInfoDTO requestInfoDTO) {

        BloodRequestInfoResponseDTO responseDTO = bloodRequestService.createRequest(requestInfoDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BloodRequestInfoResponseDTO> updateBloodRequestInfo(@PathVariable Long id, @RequestBody BloodRequestInfoUpdateRequestDTO requestInfoDTO) {
        
        BloodRequestInfoResponseDTO responseDTO = bloodRequestService.updateRequest(id, requestInfoDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
