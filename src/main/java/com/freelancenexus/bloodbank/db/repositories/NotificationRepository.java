package com.freelancenexus.bloodbank.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelancenexus.bloodbank.db.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
