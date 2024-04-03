package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>,
                                           JpaSpecificationExecutor <Product>,
                                           QuerydslPredicateExecutor<Product>
{

}
