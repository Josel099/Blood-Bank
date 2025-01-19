package com.freelancenexus.bloodbank.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelancenexus.bloodbank.db.entities.BloodRequestInfo;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequestInfo, Long> {

}
