package com.freelancenexus.bloodbank.db.entities;

import com.freelancenexus.bloodbank.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, name = "user_name")
    private String userName;

    @Column(nullable = false, name = "password")
    private String password;
    
    @Column(nullable = false, name = "role")
    private Roles role;
}
