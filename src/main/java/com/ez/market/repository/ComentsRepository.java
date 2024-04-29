package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Coments;

import java.util.List;

public interface ComentsRepository extends JpaRepository<Coments, Integer>
{
    List<Coments> findByPnum(int pid);
}