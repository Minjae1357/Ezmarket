package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>
{
	Category findBycNum(int cnum);
	List<Category> findBycKind(String kind);
}
