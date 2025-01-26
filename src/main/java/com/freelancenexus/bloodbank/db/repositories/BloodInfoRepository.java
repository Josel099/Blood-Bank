package com.freelancenexus.bloodbank.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelancenexus.bloodbank.db.entities.BloodInfo;
import com.freelancenexus.bloodbank.enums.BloodGroup;

@Repository
public interface BloodInfoRepository extends JpaRepository<BloodInfo, Long> {

    BloodInfo findByBloodGroup(BloodGroup bloodGroup);

    List<BloodInfo> findByQuantityLessThan(int i);

}
