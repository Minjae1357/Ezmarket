package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.repository.ImgsRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImgsService {
	@Autowired
	ImgsRepository imgRepo;

	public boolean saveImg(MultipartFile[] files, int pid) {
		//상대 경로를 통해서 저장하지만 외부저장소를 사용하거나 절대경로를 사용하는게 좋음.
		String uploadPath = "D:/java_workspace/Ezmarket/src/main/resources/static/images/";
		// 이미지 파일을 업로드하고 저장된 경로를 받음
	    try {
	    	for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String filePath = uploadPath + File.separator + fileName;
                File dest = new File(filePath);
                file.transferTo(dest); // 파일 저장
                String imagePath = "/images/" + fileName; // 이미지 경로
                Imgs img = new Imgs();
                img.setImgSrc(imagePath);
                img.setProductId(pid);
                imgRepo.save(img);
	    	}
	    	return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public List<Imgs> findImgsByPId(List<Product> plist){
		List<Integer> pIdList = new ArrayList<>();
		for(Product p :plist) {
			pIdList.add(p.getProductId());
		}
		return imgRepo.findImgsByProductIdIn(pIdList);
	}
	
}
