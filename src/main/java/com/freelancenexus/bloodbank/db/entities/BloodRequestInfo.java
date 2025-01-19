package com.freelancenexus.bloodbank.db.entities;

import java.time.ZonedDateTime;

import com.freelancenexus.bloodbank.enums.RequestStatus;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "blood_request_info")
public class BloodRequestInfo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "requested_time")
    private ZonedDateTime requestedTime;

    @Column(name = "approved_time")
    private ZonedDateTime approvedTime;

    @Column(name = "transaction_completed_time")
    private ZonedDateTime transactionCompletedTime;

}