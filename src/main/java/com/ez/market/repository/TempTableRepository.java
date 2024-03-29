package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.TempTable;

public interface TempTableRepository extends JpaRepository<TempTable,Integer>
{
	TempTable findByEmail(String email);
	void deleteByEmail(String email);
}
 