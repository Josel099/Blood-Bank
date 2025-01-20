package com.freelancenexus.bloodbank.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancenexus.bloodbank.dto.responses.BloodInfoResponse;
import com.freelancenexus.bloodbank.service.BloodInfoService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/blood_info")
public class BloodInfoController {

    private final BloodInfoService bloodInfoService;


    /**
     * @param bloodInfoService
     */
    public BloodInfoController(BloodInfoService bloodInfoService) {
        super();
        this.bloodInfoService = bloodInfoService;
    }

    @GetMapping
    public ResponseEntity<List<BloodInfoResponse>> getAllBloodInfo() {
        List<BloodInfoResponse> response = bloodInfoService.getAllBloodInfo();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodInfoResponse> updateBloodQuantity(@PathVariable Long id,
        @RequestParam Integer quantity) {
        BloodInfoResponse bloodInfoResponse = bloodInfoService
            .updateBloodInfo(id,quantity);
        return new ResponseEntity<>(bloodInfoResponse, HttpStatus.OK);
    }

}
