package com.ez.market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.dto.ProductBoard;
import com.ez.market.service.ImgsService;
import com.ez.market.service.ProductBoardService;
import com.ez.market.service.ProductService;

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
	public String productDetail(Model m,@PathVariable int pid) {
		List<Product>
		
		return"product/productDetail";
	}
	
}
