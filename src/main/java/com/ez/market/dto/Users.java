package com.ez.market.dto;

import org.springframework.lang.NonNull;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String phone;
	@Column(nullable = false)
	private java.sql.Date regdate;
	@Column(nullable = false)
	private String enabled;
	@Column(nullable = false)
	private String gender;
	@Column(nullable = false)
	private int age;
}
