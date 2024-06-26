package com.ez.market.dto;

import java.sql.Date;


import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Users 
{
	@Id
	@NonNull
	private String userid;
	@Column(nullable = false) 
	private String userpwd;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private int phone;
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date regdate;
	@Column(nullable = false)
	private String enabled;
}
