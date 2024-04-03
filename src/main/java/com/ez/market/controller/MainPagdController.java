package com.ez.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//메인페이지관련된 요청은여기서
@Controller
@RequestMapping("/main")
public class MainPagdController 
{
	@GetMapping("menu")
	public String main(Model m) {
		//메인페이지 장바구니아이콘에 숫자 디폴트값을 0으로해두고만들어야한다.
		m.addAttribute("count" ,3);
		return "main/menu";
	}
	
	@GetMapping("close")
	public String close(Model m) {
		
		return"product/productboard";
	}
}
