package com.ez.market.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.dto.ProductList;
import com.ez.market.repository.ImgsRepository;
import com.ez.market.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	ProductList productlist;
	@Autowired
	ImgsRepository imgsrepo;
	
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
