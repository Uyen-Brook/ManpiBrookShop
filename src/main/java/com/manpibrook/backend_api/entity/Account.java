package com.manpibrook.backend_api.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.manpibrook.backend_api.entity.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name="accounts")
public class Account {
  	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(unique = true, nullable = false)
    private String email; // 🔑 login bằng email

    @Column(nullable = false)
    private String password;
	
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@Column(name = "start_date")
	private LocalDateTime startDate;
	
	@Enumerated(EnumType.STRING)
	private ERole role;
	@Column(name = "is_active")
	private boolean isActive;
}
