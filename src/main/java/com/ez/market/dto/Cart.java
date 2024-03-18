package com.ez.market.dto;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Cart 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	private int cnum;
	@Column(nullable = false)
	private String pnum;
	@Column(name = "userid" ,nullable = false)
	private String userid;
	@Column(nullable = false)
	private java.sql.Date cdate;
}
