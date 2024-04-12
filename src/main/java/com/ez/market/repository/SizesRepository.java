package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.Cart;
import com.ez.market.dto.Sizes;


public interface SizesRepository extends JpaRepository<Sizes,Integer>,
										 JpaSpecificationExecutor <Sizes>, 
										 QuerydslPredicateExecutor<Sizes>
{

	List<Sizes> findByProductId(int pid);


}
