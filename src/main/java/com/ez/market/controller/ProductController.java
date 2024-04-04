package com.ez.market.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ez.market.dto.Brands;
import com.ez.market.dto.Colors;
import com.ez.market.dto.Product;
import com.ez.market.dto.ProductList;
import com.ez.market.dto.Sizes;
import com.ez.market.service.BrandsService;
import com.ez.market.service.CategoryService;
import com.ez.market.service.ColorsService;
import com.ez.market.service.ImgsService;
import com.ez.market.service.ProductBoardService;
import com.ez.market.service.ProductService;
import com.ez.market.service.SizeService;
import com.nimbusds.openid.connect.sdk.claims.Gender;

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
	@Autowired
	SizeService sizeSvc;
	@Autowired
	ColorsService colorSvc;
	@Autowired
	ProductBoardService PBSvc;
	@Autowired
	ProductService pSvc;
	@Autowired
	ImgsService imgSvc; 
	
	
	//브랜드 추가하기
	@GetMapping("/uploadBrandForm")
	public String getBrandForm()
	{
		return "product/addbrand";
	}
	
	//카테고리 추가하기
		@GetMapping("/uploadCategoryForm")
		public String uploadCategory() {
			return "product/uploadCategory";
		}
		@GetMapping("/addColorform")
		public String addColorform() {
			return "product/addcolor";
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
	@PostMapping("/addcolor")
	@ResponseBody
	public Map<String,Object> addcolor(@RequestParam("cColor")String color){
		System.out.println(color);
		boolean ox = colorSvc.saveColor(color);
		System.out.println("ox실행됨?"+ox);
		Map<String,Object> map = new HashMap<>();
		map.put("ox", ox);
		return map;
	}

	
	@PostMapping("/addCategory")
	@ResponseBody
	public Map<String,Boolean> addCategory(@RequestParam("cKind")String cKind,@RequestParam("gender") int cGender)
	{
		log.info("카테고리" + cKind,"성별"+cGender);
		Map<String,Boolean> map = new HashMap<>();
		boolean addCate = cateSvc.saveCategory(cKind,cGender);
		map.put("added",addCate);
		return map;
	}
	
	//상품 추가하기(상품게시판/상품/색상사이즈/이미지)
	@GetMapping("uploadProduct")
	public String uploadProduct(Model model) {
		model.addAttribute("brands", brandSvc.getAllBrands());
        model.addAttribute("categories", cateSvc.getAllCategory());
        model.addAttribute("colors", colorSvc.getAllColors());
		return "product/uploadProduct";
	}
	
	@PostMapping("/addProduct")
	@ResponseBody
	@Transactional
	public Map<String,Boolean> addProduct(@ModelAttribute Product product, @ModelAttribute Sizes sizes, @RequestParam("files") MultipartFile[] file) {
		Map<String,Boolean> map = new HashMap<>();
		System.out.println(product);
		System.out.println(sizes);
		//상품게시판 데이터 추가하기
		int pnum = PBSvc.savePB();
		//색상과 사이즈 테이블에 데이터 추가하기
		//상품테이블에 데이터 추가하기
		int pid = pSvc.savePrduct(product,pnum);
		boolean addP = sizeSvc.saveSize(sizes,pid);
		boolean addI = imgSvc.saveImg(file, pid);
		map.put("added", addP&&addI);
		//이미지테이블에 데이터 추가하기
		return map;
	}
	@GetMapping("/productList")
	public String productList(Model m) {
		List<ProductList> list = pSvc.getProductList();
		m.addAttribute("products",list);
		return"product/productlist";
	}
	@GetMapping("/detail")
	public String ProductDetail(@RequestParam("productName") String productName,Model m) {
		System.out.println("이거실행되는거맞지 ?"+productName);
		return "product/detail";
	}
	
}
	
