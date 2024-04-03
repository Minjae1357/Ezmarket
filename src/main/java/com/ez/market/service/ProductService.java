package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.dto.Product;
import com.ez.market.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	
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
}
