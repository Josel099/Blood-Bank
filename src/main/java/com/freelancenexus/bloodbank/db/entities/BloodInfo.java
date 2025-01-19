package com.freelancenexus.bloodbank.db.entities;

import com.freelancenexus.bloodbank.enums.BloodGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blood_info")
@Getter
@Setter
public class BloodInfo extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(nullable = false, name = "quantity")
    private Integer quantity;

}
