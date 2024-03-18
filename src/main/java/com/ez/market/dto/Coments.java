package com.ez.market.dto;

import org.springframework.lang.NonNull;

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
public class Coments 
{
	@Id
	@NonNull
	@Column(name = "con_seq")
	@SequenceGenerator(sequenceName="coment_seq", allocationSize=1, name="coment_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coment_seq")
	private int cnum;
	
	@Column(nullable = false)
	private int pnum;
	@Column(nullable = false)
	private String userid;

	private String contents;

	private int val;
	@Column(name = "con_piture")
	private String conPiture;
	@Column(name = "con_date",nullable = false)
	private java.sql.Date conDate;
}