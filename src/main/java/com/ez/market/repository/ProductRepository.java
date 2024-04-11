package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>,
                                           JpaSpecificationExecutor <Product>,
                                           QuerydslPredicateExecutor<Product>
{
	List<Product> findProductsByPnumIn(List<Integer> pnumList);
	Product findByProductId(int productId);
}
