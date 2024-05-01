package com.ez.market.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ez.market.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.ProductBoard;
import com.ez.market.dto.QCategory;
import com.ez.market.dto.QProductBoard;
import com.ez.market.repository.ProductBoardRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service 
public class ProductBoardService {
	@Autowired
	ProductBoardRepository pbRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public int savePB() {
		try {
			// 현재 날짜 생성
			LocalDate currentDate = LocalDate.now();
			// ProductBoard 객체 생성 및 값 설정
			ProductBoard productBoard = new ProductBoard();
			productBoard.setPdate(java.sql.Date.valueOf(currentDate));
			productBoard.setPHit(0); // 초기값 0 설정
			// ProductBoard 저장
			ProductBoard savedPB = pbRepo.save(productBoard);
			return savedPB.getPnum(); // 저장 성공
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to save ProductBoard"); // 저장 실패
		}	
	}
	
	public List<ProductBoard> findTop30ByOrderByPnumDesc() {
        return pbRepo.findTop30ByOrderByPnumDesc();
    }  

	public List<ProductBoard> getTop() {
        QProductBoard qProductBoard = new QProductBoard("pb");
        return new JPAQueryFactory(entityManager)
                .select(qProductBoard)
                .from(qProductBoard)
                .orderBy(qProductBoard.pnum.desc())
                .where()
                .limit(30)
                .fetch(); 
    }

	public List<ProductBoard> findByPnum(List<Product> plist){
		List<ProductBoard> pblist = new ArrayList<>();
		for (Product product : plist){
			int pnum = product.getPnum();

			List<ProductBoard> productBoard = pbRepo.findByPnum(pnum);
			pblist.addAll(productBoard);
		}
		return  pblist;
	}

	public boolean increaseHit(int pnum){
		try{
		ProductBoard pb = pbRepo.findByPnum(pnum).get(0);
		int currentHit = pb.getPHit();
			pb.setPHit(currentHit + 1);
			pbRepo.save(pb);
		return true;
		}catch(Exception e) {
			return false;
		}

	}

}
