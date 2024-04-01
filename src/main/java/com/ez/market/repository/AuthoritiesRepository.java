package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ez.market.dto.Authorities;
import java.util.List;



public interface AuthoritiesRepository extends JpaRepository<Authorities,Integer>
{
	boolean findByAuthority(String authority);
}
