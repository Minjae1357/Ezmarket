package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>
{

}