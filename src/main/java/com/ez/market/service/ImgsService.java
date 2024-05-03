package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.repository.ImgsRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;

@Service
public class ImgsService {
	@Autowired
	ImgsRepository imgRepo;
	@Autowired
	private ResourceLoader resourceLoader;

	public boolean saveImg(MultipartFile[] files, int pid) {
		String projectPath = "";
		// 현재 프로젝트 경로 찾기
		try {
			URI classpathRootUri = resourceLoader.getResource("classpath:").getURI();
	        File classpathRoot = new File(classpathRootUri);
	        
	        // '/C:/eclipse/workspace/Ezmarket/target/classes/'가 리턴되는데
	        // '/C:/eclipse/workspace/Ezmarket/' 까지만 나오게 두 번 상위 디렉터리로 이동
	        File projectRoot = classpathRoot.getParentFile().getParentFile();
	        projectPath = projectRoot.getAbsolutePath();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		String uploadPath = projectPath + "/src/main/resources/static/images/";
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
		List<Imgs> imgList = new ArrayList<>();
		for(Product p :plist) {
			List<Imgs> ilist = imgRepo.findByProductId(p.getProductId());
			imgList.add(ilist.get(0));
		}
		return imgList;
	}
	
	public List<Imgs> findAllByProductId(int pid) {
		
		return imgRepo.findAllByProductId(pid);
	}
}
