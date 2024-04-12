package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.ProductBoard;

public interface ProductBoardRepository extends JpaRepository<ProductBoard,Integer>,
JpaSpecificationExecutor<ProductBoard>,
QuerydslPredicateExecutor<ProductBoard>
{
	List<ProductBoard> findTop30ByOrderByPnumDesc();
}
