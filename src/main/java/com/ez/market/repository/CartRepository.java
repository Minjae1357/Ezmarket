package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer>
{

}
