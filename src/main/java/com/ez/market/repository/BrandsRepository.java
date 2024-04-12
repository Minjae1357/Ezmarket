package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Brands;

public interface BrandsRepository extends JpaRepository<Brands,Integer>{

	Brands findByBrandId(int brandId);

}
