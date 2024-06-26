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
	
	private String contents;
	@Column(nullable=false)
	private int val;
	@Column(name = "con_picture")
	private String conPicture;
	@Column(name = "con_date",nullable = false)
	private java.sql.Date conDate;
	@Column(nullable = false)
	private String userid;
	@Column(nullable = false)
	private int pnum;
}