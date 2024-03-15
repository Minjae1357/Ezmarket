package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.ProductBoard;



public interface ProductBoardRepository extends JpaRepository<ProductBoard,Integer>
{

}
