package com.freelancenexus.bloodbank.service.impl;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.freelancenexus.bloodbank.db.entities.BloodInfo;
import com.freelancenexus.bloodbank.db.entities.BloodRequestInfo;
import com.freelancenexus.bloodbank.db.entities.User;
import com.freelancenexus.bloodbank.db.repositories.BloodInfoRepository;
import com.freelancenexus.bloodbank.db.repositories.BloodRequestRepository;
import com.freelancenexus.bloodbank.db.repositories.UserRepository;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoDTO;
import com.freelancenexus.bloodbank.dto.requests.BloodRequestInfoUpdateRequestDTO;
import com.freelancenexus.bloodbank.dto.responses.BloodRequestInfoResponseDTO;
import com.freelancenexus.bloodbank.enums.BloodGroup;
import com.freelancenexus.bloodbank.enums.RequestStatus;
import com.freelancenexus.bloodbank.enums.Roles;
import com.freelancenexus.bloodbank.enums.Urgency;
import com.freelancenexus.bloodbank.mappers.BloodRequestMapper;
import com.freelancenexus.bloodbank.service.BloodRequestService;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {

    private final UserRepository userRepository;

    private final BloodRequestMapper bloodRequestMapper;

    private final BloodRequestRepository bloodRequestRepository;

    private final BloodInfoRepository bloodInfoRepository;


    /**
     * @param user
     * @param bloodRequestMapper
     * @param bloodRequestRepository
     */
    public BloodRequestServiceImpl(UserRepository userRepository,
        BloodRequestMapper bloodRequestMapper, BloodRequestRepository bloodRequestRepository,
        UserRepository userRepository2, BloodInfoRepository bloodInfoRepository) {
        super();
        this.userRepository = userRepository2;
        this.bloodRequestMapper = bloodRequestMapper;
        this.bloodRequestRepository = bloodRequestRepository;
        this.bloodInfoRepository = bloodInfoRepository;
    }

    /**
     * Creates a new blood request and persists it in the database.
     *
     * @param requestInfoDTO DTO containing details of the blood request.
     * @return A response DTO with details of the created blood request.
     *
     * Steps:
     * - Fetches the user initiating the request.
     * - Maps the DTO to the entity and assigns default values (e.g., urgency, request time, status).
     * - Sets blood group manually if the user is hospital staff because the hospital staff can request 
     *   for any type of blood but normal user can only request for their registered blood group.
     * - Persists the blood request in the database and returns the response DTO.
     *
     * Notes:
     * - For donors, urgency is defaulted to LOW if not provided.
     * - Validates that the user exists before processing the request.
     */
    @Override
    public BloodRequestInfoResponseDTO createRequest(BloodRequestInfoDTO requestInfoDTO) {

        User user = userRepository.findById(requestInfoDTO.getUserId()).get();

        BloodRequestInfo bloodRequestInfo = bloodRequestMapper
            .toBloodRequestInfoEntity(requestInfoDTO, user);

        bloodRequestInfo.setRequestedTime(ZonedDateTime.now());
        bloodRequestInfo.setStatus(RequestStatus.PENDING);

        // Set default urgency if the request does not include it. For donors there is no urgency.
        if (bloodRequestInfo.getUrgency() == null) {
            bloodRequestInfo.setUrgency(Urgency.LOW);
        }

        /**
         * if the user is hospital staff the required blood group is assign in request entity according to the staff requested.
         */
        if (user.getRole() == Roles.HOSPITAL_STAFF) {
            bloodRequestInfo.setBloodGroup(requestInfoDTO.getBloodGroup());
        }

        BloodRequestInfo responseEntity = bloodRequestRepository.save(bloodRequestInfo);
        return bloodRequestMapper.toResponseDTO(responseEntity);
    }

    /**
     * Updates an existing blood request based on the provided details.
     *
     * @param requestId ID of the blood request to be updated.
     * @param requestInfoDTO DTO containing updated details of the blood request.
     * @return A response DTO with the updated blood request details.
     *
     * Steps:
     * - Fetches the blood request by ID; throws an exception if not found.
     * - Updates the request entity with the provided details using the mapper.
     * - Handles status-specific updates:
     *   - If approved, sets the approval time.(approval can only done by Admiin)
     *   - If the transaction is completed, updates the blood inventory and sets
     *     the transaction completion time.(transaction can only completed by hospital staff).
     * - Saves the updated blood request and returns the response DTO.
     *
     * Notes:
     * - Ensures sufficient blood is available in the inventory for receiver requests.
     * - Adjusts blood stock based on donor or receiver actions.
     */

    @Override
    public BloodRequestInfoResponseDTO updateRequest(Long requestId,
        BloodRequestInfoUpdateRequestDTO requestInfoDTO) {

        BloodRequestInfo bloodRequestInfo = bloodRequestRepository.findById(requestId).orElseThrow(
            () -> new RuntimeException("Blood request not found with ID: " + requestId)
        );

        bloodRequestMapper.updateBloodRequestFromDTO(requestInfoDTO, bloodRequestInfo);

        if (requestInfoDTO.getStatus() == RequestStatus.APPROVED) {
            bloodRequestInfo.setApprovedTime(ZonedDateTime.now());
        } else if (requestInfoDTO.getStatus() == RequestStatus.TRANSACTION_COMPLETED) {
            bloodInfoUpdation(
                bloodRequestInfo.getUser().getBloodGroup(), bloodRequestInfo.getQuantity(),
                bloodRequestInfo.getUser().getRole()
            );
            bloodRequestInfo.setTransactionCompletedTime(ZonedDateTime.now());
        }

        BloodRequestInfo response = bloodRequestRepository.save(bloodRequestInfo);
        return bloodRequestMapper.toResponseDTO(response);
    }

    /**
     * Updates blood inventory based on the role and quantity of the request.
     *
     * @param bloodGroup Blood group for which inventory is being updated.
     * @param quantity Quantity of blood being added or removed.
     * @param roles Role of the user (DONOR, RECEIVER, or HOSPITAL_STAFF).
     *
     * Steps:
     * - Fetches the current blood inventory for the specified blood group.
     * - Based on the role:
     *   - For RECEIVER or HOSPITAL_STAFF, reduces inventory if sufficient stock exists.
     *   - For DONOR, increases the inventory by the specified quantity.
     * - Saves the updated inventory to the database.
     *
     * Exceptions:
     * - Throws a RuntimeException if there is insufficient blood for a receiver's request.
     */
    private void bloodInfoUpdation(BloodGroup bloodGroup, Integer quantity, Roles roles) {

        BloodInfo bloodInfo = bloodInfoRepository.findByBloodGroup(bloodGroup);

        if (roles == Roles.RECEIVER || roles == Roles.HOSPITAL_STAFF) {

            if (bloodInfo.getQuantity() >= quantity) {
                bloodInfo.setQuantity(bloodInfo.getQuantity() - quantity);
            } else {
                throw new RuntimeException("Suffitient blood is not available");
            }
            bloodInfoRepository.save(bloodInfo);

        } else if (roles == Roles.DONOR) {
            if (bloodInfo.getQuantity() != null) {
                bloodInfo.setQuantity(bloodInfo.getQuantity() + quantity);
            } else {
                bloodInfo.setQuantity(quantity);
            }

            bloodInfoRepository.save(bloodInfo);

        }
        ;
    }

    /**
     * Retrieves a paginated and sorted list of all blood requests.
     *
     * @param page Page number to retrieve (0-based).
     * @param size Number of items per page.
     * @param sort Sorting criteria in the format "field,direction" (e.g., "id,asc").
     * @return A paginated list of response DTOs for all blood requests.
     **/
    @Override
    public Page<BloodRequestInfoResponseDTO> getAllRequests(int page, int size, String sort) {
        Pageable pageable = constructPageable(page, size, sort);
        Page<BloodRequestInfo> requests = bloodRequestRepository.findAll(pageable);
        return requests.map(bloodRequestMapper::toResponseDTO);
    }

    /**
     * Retrieves a paginated and sorted list of blood requests filtered by status.
     *
     * @param status Status of the blood requests to filter (e.g., PENDING, APPROVED).
     * @param page Page number to retrieve (0-based).
     * @param size Number of items per page.
     * @param sort Sorting criteria in the format "field,direction" (e.g., "id,asc").
     * @return A paginated list of response DTOs for filtered blood requests.
     **/
    @Override
    public Page<BloodRequestInfoResponseDTO> getRequestsByStatus(RequestStatus status, int page,
        int size, String sort) {
        Pageable pageable = constructPageable(page, size, sort);
        Page<BloodRequestInfo> requests = bloodRequestRepository.findByStatus(status, pageable);
        return requests.map(bloodRequestMapper::toResponseDTO);
    }

    /**
     * Retrieves a paginated and sorted list of blood requests for a specific user.
     *
     * @param userId ID of the user whose requests are being fetched.
     * @param page Page number to retrieve (0-based).
     * @param size Number of items per page.
     * @param sort Sorting criteria in the format "field,direction" (e.g., "id,asc").
     * @return A paginated list of response DTOs for the user's blood requests.
     **/
    @Override
    public Page<BloodRequestInfoResponseDTO> getRequestsByUserId(Long userId, int page, int size,
        String sort) {
        Pageable pageable = constructPageable(page, size, sort);
        Page<BloodRequestInfo> requests = bloodRequestRepository.findByUserId(userId, pageable);
        return requests.map(bloodRequestMapper::toResponseDTO);
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
