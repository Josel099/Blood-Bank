package com.freelancenexus.bloodbank.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoDTO;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.BloodRequestInfoResponseDTO;
import com.freelancenexus.bloodbank.enums.RequestStatus;
import com.freelancenexus.bloodbank.service.BloodRequestService;

@CrossOrigin
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
    public ResponseEntity<BloodRequestInfoResponseDTO> updateBloodRequestInfo(@PathVariable Long id,
        @RequestBody BloodRequestInfoUpdateRequestDTO requestInfoDTO) {

        BloodRequestInfoResponseDTO responseDTO = bloodRequestService
            .updateRequest(id, requestInfoDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BloodRequestInfoResponseDTO>> getAllRequests(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(bloodRequestService.getAllRequests(page, size, sort));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<BloodRequestInfoResponseDTO>> getRequestsByStatus(
        @PathVariable RequestStatus status, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(bloodRequestService.getRequestsByStatus(status, page, size, sort));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BloodRequestInfoResponseDTO>> getRequestsByUserId(
        @PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(bloodRequestService.getRequestsByUserId(userId, page, size, sort));
    }

}
