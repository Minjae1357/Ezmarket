package com.ez.market.controller;

import com.ez.market.dto.Imgs;
import com.ez.market.dto.Product;
import com.ez.market.dto.ProductBoard;
import com.ez.market.service.ImgsService;
import com.ez.market.service.ProductBoardService;
import com.ez.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ez.market.service.CartService;

import java.util.List;

//메인페이지관련된 요청은여기서
@Controller
@RequestMapping("/main")
public class MainPageController 
{
	@Autowired
	CartService cartsvc;
	@Autowired
	ImgsService imgSvc;
	@Autowired
	ProductService pSvc;
	@Autowired
	ProductBoardService PBSvc;

	@GetMapping("menu")
	public String main(Model m) {
		//메인페이지 장바구니아이콘에 숫자 디폴트값을 0으로해두고만들어야한다.
		
		int count = cartsvc.cartCount();
		List<ProductBoard> pblist = PBSvc.findTop30ByOrderByPnumDesc();
		System.out.println("pblist" + pblist);
		List<Product> plist = pSvc.findProductsByPnums(pblist);
		List<Imgs> ilist = imgSvc.findImgsByPId(plist);
		m.addAttribute("productBoard",pblist);
		m.addAttribute("product",plist);
		m.addAttribute("img",ilist);
		m.addAttribute("count" ,count);
		return "main/menu";
	}
	
	@GetMapping("close")
	public String close(Model m) {
		
		return"product/productboard";
	}
}
