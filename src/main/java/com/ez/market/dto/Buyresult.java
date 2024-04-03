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
public class Buyresult
{
	@Id
	@NonNull
	@Column(name = "buy_num")
	@SequenceGenerator(sequenceName="buyres_seq", allocationSize=1, name="buyres_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyres_seq")
	private int buyNum;

	@Column(name = "product_id", nullable = false)
	private int productId;
	@Column(nullable = false)
	private String userid;
}
