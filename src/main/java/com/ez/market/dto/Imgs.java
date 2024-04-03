package com.ez.market.dto;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Imgs {

	@Id
	@NonNull
	@SequenceGenerator(sequenceName="img_seq", allocationSize=1, name="img_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "img_seq_gen")
	@Column(name="imgnum")
	private int imgnum;
	
	@Column(name="img_src",nullable=false)
	private String imgSrc;
	@Column(name="product_id",nullable=false)
	private int productId;
}
