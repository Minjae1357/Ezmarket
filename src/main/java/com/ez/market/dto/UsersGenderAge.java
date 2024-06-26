package com.ez.market.dto;



import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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
@Service
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
