package com.manpibrook.backend_api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.manpibrook.backend_api.entity.enums.Role;

import jakarta.annotation.Generated;
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
@Table(name="ACCOUNT")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String userName;
	private String password;
	private String email;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String urlImage;
	private String fullName;
	private String phoneNumber;
	private Integer Age;
	private boolean isActive;
}
