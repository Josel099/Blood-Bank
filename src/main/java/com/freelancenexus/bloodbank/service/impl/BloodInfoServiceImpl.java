package com.freelancenexus.bloodbank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.freelancenexus.bloodbank.db.entities.BloodInfo;
import com.freelancenexus.bloodbank.db.repositories.BloodInfoRepository;
import com.freelancenexus.bloodbank.dto.responses.BloodInfoResponse;
import com.freelancenexus.bloodbank.mappers.BloodInfoMapper;
import com.freelancenexus.bloodbank.service.BloodInfoService;

@Service
public class BloodInfoServiceImpl implements BloodInfoService {

    private final BloodInfoRepository bloodInfoRepository;

    private final BloodInfoMapper bloodInfoMapper;


    /**
     * @param bloodInfoRepository
     */
    public BloodInfoServiceImpl(BloodInfoRepository bloodInfoRepository,
        BloodInfoMapper bloodInfoMapper) {
        super();
        this.bloodInfoRepository = bloodInfoRepository;
        this.bloodInfoMapper = bloodInfoMapper;
    }

    @Override
    public List<BloodInfoResponse> getAllBloodInfo() {
        List<BloodInfo> bloodInfoList = bloodInfoRepository.findAll();

        return bloodInfoList.stream()
            .map(
                bloodInfo -> new BloodInfoResponse(
                    bloodInfo.getId(), bloodInfo.getBloodGroup(), bloodInfo.getQuantity()
                )
            ).collect(Collectors.toList());
    }

    @Override
    public BloodInfoResponse updateBloodInfo(Long id, Integer quantity) {

        BloodInfo bloodInfo = bloodInfoRepository.findById(id).get();

        bloodInfo.setQuantity(quantity);

        BloodInfo result = bloodInfoRepository.save(bloodInfo);
        return bloodInfoMapper.toResponse(result);

    }

}
