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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/blood_request")
@Tag(name = "Blood Requests", description = "APIs for managing blood requests")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;


    /**
     * @param bloodRequestService
     */
    public BloodRequestController(BloodRequestService bloodRequestService) {
        super();
        this.bloodRequestService = bloodRequestService;
    }

    @Operation(summary = "Create a new blood request", description = "Create a blood request with details like user, blood group, and quantity.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Blood request details", required = true, content = @Content(schema = @Schema(implementation = BloodRequestInfoDTO.class))), responses = {
        @ApiResponse(responseCode = "200", description = "Successfully created the blood request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<BloodRequestInfoResponseDTO> bloodRequestCreateRequest(
        @RequestBody BloodRequestInfoDTO requestInfoDTO) {

        BloodRequestInfoResponseDTO responseDTO = bloodRequestService.createRequest(requestInfoDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing blood request", description = "Update details of a blood request using its ID.", parameters = @Parameter(name = "id", description = "ID of the blood request to update", required = true, example = "1"), requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated blood request details", required = true, content = @Content(schema = @Schema(implementation = BloodRequestInfoUpdateRequestDTO.class))), responses = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the blood request", content = @Content(schema = @Schema(implementation = BloodRequestInfoResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BloodRequestInfoResponseDTO> updateBloodRequestInfo(@PathVariable Long id,
        @RequestBody BloodRequestInfoUpdateRequestDTO requestInfoDTO) {

        BloodRequestInfoResponseDTO responseDTO = bloodRequestService
            .updateRequest(id, requestInfoDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all blood requests", description = "Retrieve all blood requests with pagination and sorting options.", parameters = {
        @Parameter(name = "page", description = "Page number for pagination", example = "0"),
        @Parameter(name = "size", description = "Number of items per page", example = "10"),
        @Parameter(name = "sort", description = "Sorting criteria", example = "id,asc")
    }, responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the requests", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BloodRequestInfoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<BloodRequestInfoResponseDTO>> getAllRequests(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(bloodRequestService.getAllRequests(page, size, sort));
    }

    @Operation(summary = "Get blood requests by status", description = "Retrieve blood requests filtered by their status, with pagination and sorting options.", parameters = {
        @Parameter(name = "status", description = "Status of the blood requests to filter by", required = true, example = "PENDING"),
        @Parameter(name = "page", description = "Page number for pagination", example = "0"),
        @Parameter(name = "size", description = "Number of items per page", example = "10"),
        @Parameter(name = "sort", description = "Sorting criteria", example = "id,asc")
    }, responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the requests", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BloodRequestInfoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<BloodRequestInfoResponseDTO>> getRequestsByStatus(
        @PathVariable RequestStatus status, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(bloodRequestService.getRequestsByStatus(status, page, size, sort));
    }

    @Operation(summary = "Get blood requests by user ID", description = "Retrieve blood requests filtered by the user ID, with pagination and sorting options.", parameters = {
        @Parameter(name = "userId", description = "ID of the user", required = true, example = "1"),
        @Parameter(name = "page", description = "Page number for pagination", example = "0"),
        @Parameter(name = "size", description = "Number of items per page", example = "10"),
        @Parameter(name = "sort", description = "Sorting criteria", example = "id,asc")
    }, responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the requests", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BloodRequestInfoResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BloodRequestInfoResponseDTO>> getRequestsByUserId(
        @PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(bloodRequestService.getRequestsByUserId(userId, page, size, sort));
    }

}
