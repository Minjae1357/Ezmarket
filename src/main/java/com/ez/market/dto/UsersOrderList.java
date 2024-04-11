package com.ez.market.dto;

import java.sql.Date;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor		
public class UsersOrderList 
{
	@Nonnull	
	String productName;
	String userId;
	Date orderDate;
	int orderNum;
	int price;
	int size;
	int cnt;
	String status;
	String picname;	
	
}
