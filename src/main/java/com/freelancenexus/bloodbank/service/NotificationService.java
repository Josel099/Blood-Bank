package com.freelancenexus.bloodbank.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.freelancenexus.bloodbank.dto.responses.NotificationResponseDTO;

public interface NotificationService {

    void checkAndAddNotifications();

    /**
     * Retrieves a paginated and sorted list of all blood requests.
     * @param page
     * @param size
     * @param sort
     * @return
     */
    Page<NotificationResponseDTO> getAllNotifications(int page, int size, String sort);

    boolean markAllAsRead(List<Long> ids);

}
