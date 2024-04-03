package com.ez.market.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ez.market.dto.Brands;
import com.ez.market.service.BrandsService;
import com.ez.market.service.CategoryService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {
	@Autowired
	BrandsService brandSvc;
	@Autowired
	CategoryService cateSvc;
	
	//브랜드 추가하기
	@GetMapping("/uploadBrand")
	public String getBrandForm()
	{
		return "product/uploadBrand";
	}
	
	@PostMapping("/addBrand")
	@ResponseBody
	public Map<String,Object> upload(HttpServletRequest request,@RequestParam("brand") String brandName,@RequestParam("files")MultipartFile[] files) 
	{
		System.out.println("brand이름");
		Map<String,Object> map = new HashMap<>();
		
		System.out.println("brand이름"+brandName);
		//브랜드 객체 생성 및 이름 설정
		Brands brand = new Brands();
	    brand.setBrandName(brandName);
	    // 이미지 파일을 저장할 경로
	    ServletContext context = request.getServletContext();
	    String uploadPath = context.getRealPath("/WEB-INF/images/brands");
	    File uploadDir = new File(uploadPath);
	     // 이미지 파일을 서버에 저장하고, 저장된 경로를 브랜드 객체에 설정
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String filePath = uploadPath + File.separator + fileName;
                File dest = new File(filePath);
                file.transferTo(dest); // 파일 저장

                // 브랜드 객체에 이미지 파일의 경로 설정
                brand.setBrandImg(filePath);
            }
            boolean addBrand = brandSvc.saveBrand(brandName,uploadPath);
            map.put("added",addBrand);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("added", false); // 실패 시 응답 설정
            map.put("error", "File upload failed: " + e.getMessage()); // 실패 이유 추가
            return map;
            // 나중에 트랜잭션으로 연결하는게 좋아보임.
        } catch (Exception e) {
            e.printStackTrace();
            map.put("added", false); // 실패 시 응답 설정
            map.put("error", "Database save failed: " + e.getMessage()); // 실패 이유 추가
            return map;
        }
	}
	
	@GetMapping("uploadCategory")
	public String uploadCategory() {
		return "product/uploadCategory";
	}
	
	@PostMapping("/addCategory")
	@ResponseBody
	public Map<String,Boolean> addCategory(@RequestParam("cKind")String cKind,@RequestParam("gender") int cGender)
	{
		Map<String,Boolean> map = new HashMap<>();
		boolean addCate = cateSvc.saveCategory(cKind,cGender);
		map.put("added",addCate);
		return map;
	}
}
	
