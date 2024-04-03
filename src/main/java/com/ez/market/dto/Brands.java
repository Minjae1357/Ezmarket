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
public class Brands {
	@Id
	@NonNull
	@Column(name="brand_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq")
    @SequenceGenerator(name="brand_seq", sequenceName="brand_seq", allocationSize=1)
	private int brandId;
	
	@Column(name="brand_name")
	private String brandName;
	
	@Column(name="brand_img")
	private String brandImg;	
}
