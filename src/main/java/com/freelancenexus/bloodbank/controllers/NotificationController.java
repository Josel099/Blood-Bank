package com.freelancenexus.bloodbank.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancenexus.bloodbank.dto.responses.NotificationResponseDTO;
import com.freelancenexus.bloodbank.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        super();
        this.notificationService = notificationService;
    }

    @Operation(summary = "Get all notifications", description = "Retrieve all notification with pagination and sorting options.", parameters = {
        @Parameter(name = "page", description = "Page number for pagination", example = "0"),
        @Parameter(name = "size", description = "Number of items per page", example = "10"),
        @Parameter(name = "sort", description = "Sorting criteria", example = "id,asc")
    }, responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the notifications", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NotificationResponseDTO.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<NotificationResponseDTO>> getAllRequests(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort) {
        return ResponseEntity.ok(notificationService.getAllNotifications(page, size, sort));
    }
    
    
    @PutMapping("/mark-as-read")
    @Operation(
        summary = "Mark multiple notifications as read",
        description = "Marks multiple notifications as read by updating the `markAsRead` field to `true` for all provided IDs.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "List of notification IDs to be marked as read",
            content = @Content(schema = @Schema(type = "array", example = "[1, 2, 3]"))
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Notifications marked as read successfully",
                content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "One or more notifications not found",
                content = @Content(schema = @Schema(implementation = String.class))
            )
        }
    )
    public ResponseEntity<String> markMultipleNotificationsAsRead(@RequestBody List<Long> ids) {
        boolean allUpdated = notificationService.markAllAsRead(ids);
        if (allUpdated) {
            return ResponseEntity.ok("All notifications marked as read successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .body("Some notifications could not be found or updated.");
        }
    }


}
