package com.ez.market.dto;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor 
@Service
public class Cart 
{
	@Id
	@SequenceGenerator(sequenceName="cart_seq", allocationSize=1, name="cart_seq_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq_gen")
	@NonNull
	private int cnum;
	@Column(name="product_id",nullable = false)
	private int productId;
	@Column(name = "userid" ,nullable = false)
	private String userid;
}
