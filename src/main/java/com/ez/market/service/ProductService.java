package com.ez.market.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.dto.ProductBoard;
import com.ez.market.dto.QProduct;
import com.ez.market.dto.ProductList;

import com.ez.market.repository.ImgsRepository;
import com.ez.market.repository.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	ProductList productlist;
	@Autowired
	ImgsRepository imgsrepo;
	
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
	
    
	
	public List<Product> getList(){
		return productRepo.findAll();
	}
	
	public List<ProductList> getProductList(){
			List<Product> list = getList();
			List<ProductList> plist = new ArrayList<>();
			for(Product p : list ) {
			Imgs imgs = imgsrepo.findByProductId(p.getProductId());		
			productlist.setProductName(p.getProductName());
			productlist.setImgName(imgs.getImgSrc());
			productlist.setPrice(p.getProductPrice());
			plist.add(productlist);
		}
			System.out.println(plist);
			return plist;
	}

}
