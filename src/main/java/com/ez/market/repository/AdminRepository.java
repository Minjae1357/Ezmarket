package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin,String>
{

}
