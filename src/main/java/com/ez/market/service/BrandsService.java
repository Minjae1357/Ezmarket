package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ez.market.dto.Brands;
import com.ez.market.repository.BrandsRepository;

@Service
public class BrandsService {
	@Autowired
	BrandsRepository brandsRepo;
	
	public boolean saveBrand(Brands brand) {
        try {
            brandsRepo.save(brand);
            return true; // 저장 성공
        } catch (Exception e) {
            return false; // 저장 실패
        }
    }
	
	
	// 브랜드 아이디를 가져옴. updatedBrand로 수정.
	public boolean updateBrand(String brandId, Brands updatedBrand) {
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
}
