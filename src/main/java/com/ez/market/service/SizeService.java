package com.ez.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.QSizes;
import com.ez.market.dto.SizeEnum;
import com.ez.market.dto.Sizes;
import com.ez.market.repository.SizesRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SizeService {
	@Autowired
	SizesRepository sizeRepo;
	@Autowired
	private JPAQueryFactory queryFactory;
	@PersistenceContext
	private EntityManager entityManager;
	public List<Sizes> getAllSizes() {
        return sizeRepo.findAll();
    }
	
	@Transactional
	public boolean saveSize(Sizes s, int pid) 
	{
		try {
			//sId는 시퀀스 사용 
			s.setProductId(pid);
			sizeRepo.save(s);
			return true;
		} catch(Exception e) {
			//e.printStackTrace(); 
            //throw new RuntimeException("Failed to save Sizes");
            return false;
		}
	}

	public List<Sizes> findByProductId(int pid) {
		return sizeRepo.findByProductId(pid);
	}
	
	@Transactional
	public void updateQty(int productId, String size, int qty) {
	    QSizes sizes = QSizes.sizes;
	    
	    // 문자열을 SizeEnum으로 변환 후 숫자값 추출
	    SizeEnum snum = SizeEnum.fromString(size);
	    int sizenum = snum.getValue();

	    // 조건에 맞는 Sizes 엔티티 조회
	    Sizes foundSize = queryFactory
	        .selectFrom(sizes)
	        .where(sizes.productId.eq(productId)
	              .and(sizes.size.eq(sizenum)))
	        .fetchOne();

	    // 재고 수량 업데이트
	    if (foundSize != null) {
	        foundSize.setInventory(qty);
	        entityManager.persist(foundSize);
	    }
	}
	
	
}
