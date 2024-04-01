package com.ez.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//메인페이지관련된 요청은여기서
@Controller
@RequestMapping("/main")
public class MainPagdController 
{
	@GetMapping("menu")
	public String main() {
		return "main/menu";
	}
}
