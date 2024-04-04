package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer>,
                                        JpaSpecificationExecutor <Cart>, 
                                        QuerydslPredicateExecutor<Cart>
{
	int countByUserid(String userid);
}
