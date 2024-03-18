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
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product 
{
	@Id
	@Column(name="product_id")
	@NonNull
	private String productId;
	@Column(name="product_price")
	private int productPrice;
	@Column(name="product_name" , nullable = false)
	private String productName;
	@Column(name="c_name",nullable = false)
	private String cname;
	@Column(name="brand_id",nullable = false)
	private String brandId;
	@Column(name="c_kind",nullable = false)
	private String ckind;
	@Column(name="imgnum")
	private int img;
	@Column(name="s_id",nullable = false)
	private String sid;
	
}
