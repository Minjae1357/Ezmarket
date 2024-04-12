package com.ez.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.dto.Brands;
import com.ez.market.dto.Category;
import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.dto.ProductBoard;
import com.ez.market.dto.Sizes;
import com.ez.market.service.BrandsService;
import com.ez.market.service.CategoryService;
import com.ez.market.service.ImgsService;
import com.ez.market.service.ProductBoardService;
import com.ez.market.service.ProductService;
import com.ez.market.service.SizeService;

@Controller
@RequestMapping("productboard")
public class ProductBoardController
{
	@Autowired
	ProductBoardService PBSvc;
	@Autowired
	ProductService pSvc;
	@Autowired
	ImgsService imgSvc;
	@Autowired
	SizeService sizeSvc;
	@Autowired
	CategoryService cateSvc;
	@Autowired
	BrandsService brandSvc;
	
	@GetMapping("list")
	public String productBoard(Model m) 
	{
		List<ProductBoard> pblist = PBSvc.findTop30ByOrderByPnumDesc();
		List<Product> plist = pSvc.findProductsByPnums(pblist);
		List<Imgs> ilist = imgSvc.findImgsByPId(plist);
		m.addAttribute("productBoard",pblist);
		m.addAttribute("product",plist);
		m.addAttribute("img",ilist);
		return "product/productMainList";
	}
	
	@GetMapping("detail/{pId}")
	public String productDetail(Model m,@PathVariable("pId") int pid) {
		Product plist = pSvc.findByProductId(pid);
		List<Imgs> ilist = imgSvc.findAllByProductId(pid);
		List<Sizes> slist = sizeSvc.findByProductId(pid);
		Category cate = cateSvc.findBycNum(plist.getCNum());
		Brands brand = brandSvc.findByBrandId(plist.getBrandId());
		m.addAttribute("product",plist);
		String mainImageSrc = ilist.get(0).getImgSrc();
	    m.addAttribute("mainImageSrc", mainImageSrc);
		m.addAttribute("img",ilist);
		m.addAttribute("Size",slist);
		m.addAttribute("category",cate);
		m.addAttribute("brand",brand);
		return"product/productDetail";
	}
	
	@PostMapping("addCart")
	@ResponseBody
	public Map<String,Boolean> addCart(@RequestParam("pid")int pid,@RequestParam("id")String userid){
		Map<String,Boolean> map = new HashMap<>();
		
		return map;
	}
	
	
	
}
