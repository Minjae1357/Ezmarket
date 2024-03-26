package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.Cart;
import com.ez.market.dto.UsersOrder;

public interface UsersOrderRepository extends JpaRepository<UsersOrder, Integer>,
												JpaSpecificationExecutor<UsersOrder>, 
												QuerydslPredicateExecutor<UsersOrder>
{
	List<UsersOrder> findByUserid(String userid);
}

/*
package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.UsersOrder;

public interface UsersOrderRepository extends JpaRepository<UsersOrder, Integer>
{
	List<UsersOrder> findByUserid(String userid);
}
*/