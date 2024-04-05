package com.ez.market.service;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.Product;
import com.ez.market.dto.ProductBoard;
import com.ez.market.dto.QProduct;
import com.ez.market.repository.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
    private JPAQueryFactory queryFactory;
	
	public int savePrduct(Product p,int pnum){
		try {
			p.setPnum(pnum);
			Product pro = productRepo.save(p);
			return pro.getProductId();
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to save Product");
		}
		
	}
	public List<Product> findProductsByPnums(List<ProductBoard> pbList) {
        List<Integer> pnumList = pbList.stream()
                                       .map(ProductBoard::getPnum)
                                       .collect(Collectors.toList());
        return productRepo.findProductsByPnumIn(pnumList);
    }
	
    
}
