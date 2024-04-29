package com.ez.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.market.dto.Sizes;
import com.ez.market.repository.SizesRepository;

@Service
public class SizeService {
	@Autowired
	SizesRepository sizeRepo;
	
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
}
