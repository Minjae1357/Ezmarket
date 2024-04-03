package com.ez.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.Imgs;

public interface ImgsRepository extends JpaRepository<Imgs, Integer>,
                                        JpaSpecificationExecutor <Imgs>, 
                                        QuerydslPredicateExecutor<Imgs>
{

}
