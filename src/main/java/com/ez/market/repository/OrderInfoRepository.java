package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer>
{
	OrderInfo findByoNum(int oNum); 
}