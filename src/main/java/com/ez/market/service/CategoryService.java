package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.dto.Category;
import com.ez.market.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository cateRepo;
	
	public boolean saveCategory(String kind, int gender) 
	{
		try {
			Category cate = new Category();
			cate.setCKind(kind);
			cate.setCGender(gender);
			cateRepo.save(cate);
			cateRepo.flush();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
