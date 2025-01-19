package com.freelancenexus.bloodbank.db.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelancenexus.bloodbank.db.entities.BloodRequestInfo;
import com.freelancenexus.bloodbank.enums.RequestStatus;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequestInfo, Long> {

    Page<BloodRequestInfo> findByStatus(RequestStatus status, Pageable pageable);

    Page<BloodRequestInfo> findByUserId(Long userId, Pageable pageable);

}
