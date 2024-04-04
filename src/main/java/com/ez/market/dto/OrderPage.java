package com.ez.market.dto;

import java.sql.Date;

import org.springframework.stereotype.Service;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Data
@Service
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class OrderPage
{
	@Nonnull
	private int cnum;
	private String productName;
	private int productPrice;
	private int productId;
	private int size;
	private String imgSrc;
}
