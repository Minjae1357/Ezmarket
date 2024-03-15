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
public class Sizes {
	@Id
	@NonNull
	@Column(name="s_id")
	private String sId;
	
	@Column(name="product_id", nullable=false)
	private String productId;
	@Column(name="s_shoulder")
	private int shoulder;
	@Column(name="s_chest")
	private int chest;
	@Column(name="s_sleeve")
	private int sleeve;
	@Column(name="s_waist")
	private int waist;
	@Column(name="s_thigh")
	private int thigh;
	@Column(name="s_rise")
	private int rise;
	@Column(name="s_legopening")
	private int legopening;
	@Column(name="size")
	private int size;
	@Column(name="inventory", nullable=false)
	private int inventory;
	
	
}
