	package com.ez.market.dto;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Service
public class Authorities 
{
	@Id
	@NonNull	//generatedvalue는 자동으로 시퀀스증가시키게하는것
	@SequenceGenerator(sequenceName="s_seq", allocationSize=1, name="s_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_seq_gen")
	@Column(name = "s_seq",nullable = false)
	private int anum;
	@Column(name = "userid",nullable = false)
	private String userid;
	@Column(nullable = false)
	private String authority;
}
