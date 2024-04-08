package com.ez.market.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ez.market.dto.Brands;
import com.ez.market.repository.BrandsRepository;

@Service
public class BrandsService {
	@Autowired
	BrandsRepository brandsRepo;
	@Autowired
	Brands brand;
	
	
	public boolean saveBrand(MultipartFile[] files,String brandName) {
			String uploadPath = "D:/java_workspace/Ezmarket/src/main/resources/static/brands/";
        try {
        	for (MultipartFile file : files) {
        		String filename = file.getOriginalFilename();
        		file.transferTo(new File(uploadPath +  File.separator + filename)); 
        		brand.setBrandImg("/brands/" +filename);
        		brand.setBrandName(brandName);
        		brandsRepo.save(brand);
        	}
            return true; // 저장 성공
        } catch (Exception e) {
            return false; // 저장 실패
        } 
    }
	
	
	// 브랜드 아이디를 가져옴. updatedBrand로 수정.
	public boolean updateBrand(int brandId, Brands updatedBrand) {
        // brandId로 해당 브랜드를 찾아서 업데이트
        Brands existingBrand = brandsRepo.findById(brandId).orElse(null);
        if (existingBrand != null) {
            // 수정할 필드 업데이트
            existingBrand.setBrandName(updatedBrand.getBrandName());
            existingBrand.setBrandImg(updatedBrand.getBrandImg());
            try {
                brandsRepo.save(existingBrand);
                return true; // 수정 성공
            } catch (Exception e) {
                return false; // 수정 실패
            }
        }
        return false; // 수정할 브랜드가 없을 경우
    }
	
	public List<Brands> getAllBrands() {
        return brandsRepo.findAll();
    }
}
