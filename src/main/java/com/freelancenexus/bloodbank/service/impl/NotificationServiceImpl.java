package com.freelancenexus.bloodbank.service.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.freelancenexus.bloodbank.db.entities.BloodInfo;
import com.freelancenexus.bloodbank.db.entities.Notification;
import com.freelancenexus.bloodbank.db.repositories.BloodInfoRepository;
import com.freelancenexus.bloodbank.db.repositories.NotificationRepository;
import com.freelancenexus.bloodbank.dto.responses.NotificationResponseDTO;
import com.freelancenexus.bloodbank.mappers.NotificationMapper;
import com.freelancenexus.bloodbank.service.NotificationService;

import jakarta.transaction.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final BloodInfoRepository bloodInfoRepository;

    private final NotificationMapper notificationMapper;


    /**
     * @param notificationRepository
     * @param bloodInfoRepository
     */
    public NotificationServiceImpl(NotificationRepository notificationRepository,
        BloodInfoRepository bloodInfoRepository, NotificationMapper notificationMapper) {
        super();
        this.notificationRepository = notificationRepository;
        this.bloodInfoRepository = bloodInfoRepository;
        this.notificationMapper = notificationMapper;
    }

    @Transactional
    @Override
    public void checkAndAddNotifications() {

        List<BloodInfo> lowStockBloodGroups = bloodInfoRepository.findByQuantityLessThan(5);

        for (BloodInfo bloodInfo : lowStockBloodGroups) {

            String message = "Low stock alert: Blood group " + bloodInfo.getBloodGroup().name()
                + " has less than 5 units.";

            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setCreatedAt(ZonedDateTime.now());
            notification.setMarkAsRead(false);
            notificationRepository.save(notification);
        }

    }

    /**
     * Retrieves a paginated and sorted list of all blood requests.
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @Override
    public Page<NotificationResponseDTO> getAllNotifications(int page, int size, String sort) {
        Pageable pageable = constructPageable(page, size, sort);
        Page<Notification> requests = notificationRepository.findAll(pageable);
        return requests.map(notificationMapper::toResponseDTO);
    }
    
    
    @Transactional
    public boolean markAllAsRead(List<Long> ids) {
        List<Notification> notifications = notificationRepository.findAllById(ids);
        
        if (notifications.isEmpty()) {
            return false; // None of the notifications found
        }

        // Mark all retrieved notifications as read
        notifications.forEach(notification -> notification.setMarkAsRead(true));
        notificationRepository.saveAll(notifications); // Bulk update the records

        // Check if any IDs were not found
        return notifications.size() == ids.size(); // True if all IDs were updated
    }

    
    

    /**
     * Constructs a Pageable object based on page number, size, and sorting criteria.
     *
     * @param page Page number to retrieve (0-based).
     * @param size Number of items per page.
     * @param sort Sorting criteria in the format "field,direction" (e.g., "id,asc").
     * @return A Pageable object configured with the specified parameters.
     **/
    private Pageable constructPageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort sorting = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        return PageRequest.of(page, size, sorting);
    }

}
