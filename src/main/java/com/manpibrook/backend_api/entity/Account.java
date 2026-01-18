package com.manpibrook.backend_api.entity;


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
@Table(name="account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private long accountId;
	@Column(name = "user_name")
	private String userName;
	private String password;
	@Column(name = "start_date")
	private Date startDate;
	@Enumerated(EnumType.STRING)
	private ERole role;
	@Column(name = "is_active")
	private boolean isActive;
}
