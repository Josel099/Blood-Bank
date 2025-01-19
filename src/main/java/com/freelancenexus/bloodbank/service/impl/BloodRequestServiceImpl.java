package com.freelancenexus.bloodbank.service.impl;

import java.time.ZonedDateTime;

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

    @Override
    public BloodRequestInfoResponseDTO createRequest(BloodRequestInfoDTO requestInfoDTO) {

        User user = userRepository.findById(requestInfoDTO.getUserId()).get();

        BloodRequestInfo bloodRequestInfo = bloodRequestMapper
            .toBloodRequestInfoEntity(requestInfoDTO, user);

        bloodRequestInfo.setRequestedTime(ZonedDateTime.now());
        bloodRequestInfo.setStatus(RequestStatus.PENDING);

        BloodRequestInfo responseEntity = bloodRequestRepository.save(bloodRequestInfo);
        return bloodRequestMapper.toResponseDTO(responseEntity);
    }

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

    private void bloodInfoUpdation(BloodGroup bloodGroup, Integer quantity, Roles roles) {

        BloodInfo bloodInfo = bloodInfoRepository.findByBloodGroup(bloodGroup);

        if (roles == Roles.RECEIVER) {

            if (bloodInfo.getQuantity() >= quantity) {
                bloodInfo.setQuantity(bloodInfo.getQuantity() - quantity);
            } else {
                throw new RuntimeException("Suffitient blood is not available");
            }

        } else if (roles == Roles.DONOR) {
            if (bloodInfo.getQuantity() != null) {
                bloodInfo.setQuantity(bloodInfo.getQuantity() + quantity);
            } else {
                bloodInfo.setQuantity(quantity);
            }
        };
    }

}
