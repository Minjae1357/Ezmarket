package com.ez.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.ez.market.dto.Imgs;

public interface ImgsRepository extends JpaRepository<Imgs, Integer>,
                                        JpaSpecificationExecutor <Imgs>, 
                                        QuerydslPredicateExecutor<Imgs>
{
	List<Imgs> findImgsByProductIdIn(List<Integer> pIdList);
	Imgs findByProductId(int productid);
	List<Imgs> findAllByProductId(int productId);
}
