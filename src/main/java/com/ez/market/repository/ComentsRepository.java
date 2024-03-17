package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Coments;

public interface ComentsRepository extends JpaRepository<Coments, Integer>
{

}