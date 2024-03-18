package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Product;


public interface ProductRepository extends JpaRepository<Product,String>
{

}
