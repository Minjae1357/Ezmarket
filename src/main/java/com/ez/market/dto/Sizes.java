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
public class Sizes {
	@Id
	@NonNull
	@Column(name="s_id")

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sizes_seq")
    @SequenceGenerator(name="sizes_seq", sequenceName="sizes_seq", allocationSize=1)
	private int sId;
	
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
	@Column(name="s_size")
	private int size;
	@Column(name="inventory", nullable=false)
	private int inventory;
	@Column(name="product_id", nullable=false)
	private int productId;
	
	
}
