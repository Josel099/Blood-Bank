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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/blood_info")
@Tag(name = "Blood Info", description = "APIs for managing blood information")
public class BloodInfoController {

    private final BloodInfoService bloodInfoService;


    /**
     * @param bloodInfoService
     */
    public BloodInfoController(BloodInfoService bloodInfoService) {
        super();
        this.bloodInfoService = bloodInfoService;
    }

    @Operation(summary = "Get all blood information", description = "Fetch a list of all blood groups and their available quantities.", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BloodInfoResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<BloodInfoResponse>> getAllBloodInfo() {
        List<BloodInfoResponse> response = bloodInfoService.getAllBloodInfo();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update blood quantity", description = "Update the quantity of a specific blood group using its ID.", parameters = {
        @Parameter(name = "id", description = "ID of the blood group", required = true, example = "1"),
        @Parameter(name = "quantity", description = "New quantity of the blood group", required = true, example = "5")
    }, responses = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the blood quantity", content = @Content(schema = @Schema(implementation = BloodInfoResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BloodInfoResponse> updateBloodQuantity(@PathVariable Long id,
        @RequestParam Integer quantity) {
        BloodInfoResponse bloodInfoResponse = bloodInfoService.updateBloodInfo(id, quantity);
        return new ResponseEntity<>(bloodInfoResponse, HttpStatus.OK);
    }

}
