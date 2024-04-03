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
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product 
{
	@Id
	@NonNull
	@Column(name="product_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name="product_seq", sequenceName="product_seq", allocationSize=1)
	private int productId;
	@Column(name="product_price")
	private int productPrice;
	@Column(name="product_name")
	private String productName;
	@Column(name="brand_id",nullable = false)
	private int brandId;
	@Column(name="c_num",nullable = false)
	private int cNum; //옷종류 참조하려고 받는 시퀀스
	@Column(name="s_id",nullable = false) // 색깔 받는 컬럼
	private int sId;
	@Column(nullable = false, name="pnum")
	private int pnum;
	
}
