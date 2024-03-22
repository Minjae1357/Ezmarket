package com.ez.market.dto;

import java.sql.Date;


import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="users_gender_age")
public class UsersGenderAge {

	@Id
	@NonNull
	private String userid;
	@Column(nullable = false)
	private String gender;
	@Column(nullable = false)
	private java.sql.Date birthday;
}
