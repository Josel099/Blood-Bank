package com.freelancenexus.bloodbank.db.entities;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    private String message;

    private ZonedDateTime createdAt;

    private Boolean markAsRead;

}
