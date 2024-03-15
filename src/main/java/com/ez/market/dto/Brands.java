package com.ez.market.dto;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private String brandId;
	
	@Column(name="brand_name")
	private String brandName;
	
	@Column(name="brand_img")
	private String brandImg;	
}
