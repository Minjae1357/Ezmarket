package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.UsersOrder;

public interface OrderRepository extends JpaRepository<UsersOrder, Integer>
{

}