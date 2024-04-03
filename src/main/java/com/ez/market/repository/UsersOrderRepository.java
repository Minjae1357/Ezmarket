package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.UsersOrder;

public interface UsersOrderRepository extends JpaRepository<UsersOrder, Integer>
{
	List<UsersOrder> findByUserid(String userid);
}
