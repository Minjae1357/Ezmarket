package com.ez.market.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Category> getAllCategory() {
        return cateRepo.findAll();
    }
	
	public Category findBycNum(int cnum) {
		return cateRepo.findBycNum(cnum);
	}

	public List<Integer> findcNumBycKind(String ckind) {
		List<Category> clist = cateRepo.findBycKind(ckind);
		List<Integer> cNumList = new ArrayList<>();
		for (Category category:clist){
			cNumList.add(category.getCNum());
		}
		return cNumList;
	}
	
}
